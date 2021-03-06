package br.com.porto.isabel.weather.mvp.home;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.formatter.DateFormatter;
import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.mvp.daily.DailyActivity;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.repository.cache.SharedPreferencesUserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCachedRepository;
import br.com.porto.isabel.weather.service.retrofit.openweather.RetrofitWeatherAPI;
import br.com.porto.isabel.weather.view.adapter.daily.DailyAdapter;
import br.com.porto.isabel.weather.view.adapter.usercity.UserCityAdapter;
import br.com.porto.isabel.weather.view.customview.DetailCustomView;
import br.com.porto.isabel.weather.view.util.IconUtil;
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

    @BindView(R.id.home_error)
    View errorView;

    private CurrentInterface mCurrent;

    private ForecastInterface mForecast;

    private IconUtil mIconUtil;

    private DailyAdapter mAdapter;

    private Spinner mSpinner;

    @BindView(R.id.try_again)
    Button tryAgainButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Gson gson = new Gson();
        UserCityCacheStrategy cacheStrategy = new SharedPreferencesUserCityCacheStrategy(getContext(), gson);
        UserCityRepository userCityRepository = new UserCityCachedRepository(cacheStrategy, gson);
        HomeModel model = new HomeModel(new RetrofitWeatherAPI(getContext()), userCityRepository);
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
        } else {
            mPresenter.init();
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

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onTryAgainClicked();
            }
        });
        return view;
    }

    @Override
    public void showCurrentData(CurrentInterface current) {
        mCurrent = current;
        cityNameTextView.setText(current.getCityName());
        weatherDescriptionTextView.setText(current.getWeatherDescription());
        temperatureTextView.setText(current.getCurrentTemperature() + "°C");
        weatherImage.setImageResource(mIconUtil.getWeatherImageResource(current.getWeatherCode()));
        humidityView.setValue(current.getHumidity() + " %");
        windView.setValue(current.getWindSpeed() + " km/h   " + current.getWindDegree() + " ° ");
        pressureView.setValue(current.getPressure() + " hPa");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT, mCurrent);
        outState.putParcelable(FORECAST, mForecast);
    }


    @Override
    public void showForecast(ForecastInterface forecast) {
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
    public void showProgress() {
        mContent.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mContent.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        mContent.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showDailyInformation(DailyInterface daily, UserCity userCity) {
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
                UserCity city = (UserCity) parent.getSelectedItem();
                mPresenter.onCitySelected(city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPresenter.onCreateOptionsMenu();

    }
}
