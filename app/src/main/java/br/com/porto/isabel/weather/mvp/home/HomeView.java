package br.com.porto.isabel.weather.mvp.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.formatter.DateFormatter;
import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.mvp.daily.DailyActivity;
import br.com.porto.isabel.weather.view.adapter.daily.DailyAdapter;
import br.com.porto.isabel.weather.view.adapter.usercity.UserCityAdapter;
import br.com.porto.isabel.weather.view.customview.DetailCustomView;
import br.com.porto.isabel.weather.view.rx.RxSpinner;
import br.com.porto.isabel.weather.view.util.IconUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by isabelporto on 17/10/2016.
 */

public class HomeView extends FrameLayout implements HomeContract.ViewContract {


    private static final String FORECAST = "FORECAST";
    private static final String CURRENT = "CURRENT";

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


    @BindView(R.id.try_again)
    Button tryAgainButton;

    private IconUtil mIconUtil;

    private DailyAdapter mAdapter;


    @BindView(R.id.home_city_selection)
    Spinner mSpinner;

    public HomeView(Context context) {
        super(context);
        inflate(getContext(), R.layout.home_fragment, this);
        ButterKnife.bind(this);
        mIconUtil = new IconUtil(this.getResources(), getContext().getPackageName());
        mAdapter = new DailyAdapter(new DateFormatter(), mIconUtil);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showCurrentData(CurrentInterface current) {
        cityNameTextView.setText(current.getCityName());
        weatherDescriptionTextView.setText(current.getWeatherDescription());
        temperatureTextView.setText(current.getCurrentTemperature() + "°C");
        weatherImage.setImageResource(mIconUtil.getWeatherImageResource(current.getWeatherCode()));
        humidityView.setValue(current.getHumidity() + " %");
        windView.setValue(current.getWindSpeed() + " km/h   " + current.getWindDegree() + " ° ");
        pressureView.setValue(current.getPressure() + " hPa");
    }


    @Override
    public void showForecast(ForecastInterface forecast) {
        mAdapter.setDailyList(forecast.getDailyList());
    }

    @Override
    public void hideSwipe() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showCityList(List<UserCity> userCityList, UserCity current) {
        UserCityAdapter adapter = new UserCityAdapter(getContext(), R.layout.spinner_item, userCityList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        if (current != null) {
            mSpinner.setSelection(adapter.getPosition(current));
        }
    }

    @Override
    public View getView() {
        return this;
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
        Intent intent = new Intent(getContext(), DailyActivity.class);
        intent.putExtra(DailyActivity.DAILY_EXTRA, daily);
        intent.putExtra(DailyActivity.USER_CITY, userCity);
        //TODO: startActivity(intent);
    }

    public Observable<UserCity> observeSelectCity(){
        return RxSpinner.selectItem(mSpinner);
    }

    @Override
    public Observable<Void> observePullToRefresh() {
        return RxSwipeRefreshLayout.refreshes(mSwipeLayout);
    }

    @Override
    public Observable<Void> observeTryAgainClick() {
        return RxView.clicks(tryAgainButton);
    }

}
