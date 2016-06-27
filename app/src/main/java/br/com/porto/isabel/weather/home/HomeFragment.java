package br.com.porto.isabel.weather.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.daily.DailyActivity;
import br.com.porto.isabel.weather.error.ErrorFragment;
import br.com.porto.isabel.weather.formatter.DateFormatter;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.SharedPreferencesUserCityRepository;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.view.DailyAdapter;
import br.com.porto.isabel.weather.view.DetailCustomView;
import br.com.porto.isabel.weather.view.IconUtil;
import br.com.porto.isabel.weather.view.UserCityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeContract.ViewContract,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String FORECAST = "FORECAST";
    private static final String CURRENT = "CURRENT";

    private HomeContract.PresenterContract mPresenter;

    @BindView(R.id.home_city_name)
    TextView cityNameTextView;

    @BindView(R.id.home_weather_description)
    TextView weatherDescriptionTextView;

    @BindView(R.id.home_temperature)
    TextView temperatureTextView;

    @BindView(R.id.home_weather_image)
    ImageView weatherImage;

    @BindView(R.id.home_humidity_view)
    DetailCustomView humidityView;

    @BindView(R.id.home_wind_view)
    DetailCustomView windView;

    @BindView(R.id.home_pressure_view)
    DetailCustomView pressureView;

    @BindView(R.id.home_daily_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.home_progress)
    View mProgress;

    @BindView(R.id.home_content)
    View mContent;

    @BindView(R.id.home_swipe_refresh)
    SwipeRefreshLayout mSwipeLayout;

    private Current mCurrent;

    private Forecast mForecast;

    private IconUtil mIconUtil;

    private DailyAdapter mAdapter;

    private Spinner mSpinner;

    private boolean firstTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firstTime = true;

        UserCityRepository userCityRepository = new SharedPreferencesUserCityRepository(getActivity(), new Gson());
        HomeModel model = new HomeModel(new WeatherAPI(), userCityRepository);
        mPresenter = new HomePresenter(this, model);
        model.setPresenter(mPresenter);

        View view = configureView(inflater, container);
        setHasOptionsMenu(true);

        Current current = null;
        Forecast forecast = null;
        if (savedInstanceState != null) {
            current = (Current) savedInstanceState.get(CURRENT);
            forecast = (Forecast) savedInstanceState.get(FORECAST);
        }

        if (current != null && forecast != null) {
            showCurrentData(current);
            showForecast(forecast);
        }

        return view;
    }

    private View configureView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        mIconUtil = new IconUtil(this.getResources(), getActivity().getPackageName());
        mAdapter = new DailyAdapter(new DateFormatter(), mIconUtil, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle(null);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary);
        return view;
    }

    @Override
    public void showProgress() {
        mContent.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCurrentData(Current current) {
        mCurrent = current;
        cityNameTextView.setText(current.getCityName());
        weatherDescriptionTextView.setText(current.getWeatherDescription());
        temperatureTextView.setText(current.getCurrentTemperature().intValue() + "°C");
        weatherImage.setImageResource(mIconUtil.getWeatherImageResource(current.getWeatherCode()));
        humidityView.setValue(current.getHumidity().intValue() + " %");
        windView.setValue(current.getWindSpeed().intValue() + " km/h   " + current.getWindDegree().intValue() + " ° ");
        pressureView.setValue(current.getPressure().intValue() + " hPa");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT, mCurrent);
        outState.putParcelable(FORECAST, mForecast);
    }


    @Override
    public void showForecast(Forecast forecast) {
        mForecast = forecast;
        mAdapter.setDailyList(forecast.getDailyList());
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void hideSwipe() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showCityList(List<UserCity> userCityList, UserCity current) {
        UserCityAdapter adapter = new UserCityAdapter(getActivity(), R.layout.spinner_item, userCityList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        if (current != null) {
            mSpinner.setSelection(adapter.getPosition(current));
        }
    }

    @Override
    public void showError() {
        getFragmentManager().
                beginTransaction().
                replace(R.id.container, new ErrorFragment()).
                addToBackStack(null).
                commit();
    }

    @Override
    public void showDailyInformation(Daily daily, UserCity userCity) {
        Intent intent = new Intent(getActivity(), DailyActivity.class);
        intent.putExtra(DailyActivity.DAILY_EXTRA, daily);
        intent.putExtra(DailyActivity.USER_CITY, userCity);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_home_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        mSpinner = (Spinner) MenuItemCompat.getActionView(item);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!firstTime || mCurrent == null) {
                    UserCity city = (UserCity) parent.getSelectedItem();
                    mPresenter.onCitySelected(city);
                }
                firstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPresenter.onCreateOptionsMenu();

    }
}
