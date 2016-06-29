package br.com.porto.isabel.weather.mvp.daily;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.formatter.DateFormatter;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.customview.DetailCustomView;
import br.com.porto.isabel.weather.view.util.IconUtil;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DailyActivity extends AppCompatActivity implements DailyContract.ViewContract {

    public static final String DAILY_EXTRA = "DAILY_EXTRA";
    public static final String USER_CITY = "USER_CITY";
    private Toolbar mToolbar;
    private DailyContract.PresenterContract mPresenter;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private IconUtil mIconUtil;

    @BindView(R.id.daily_humidity_view)
    DetailCustomView humidityView;

    @BindView(R.id.daily_wind_view)
    DetailCustomView windView;

    @BindView(R.id.daily_pressure_view)
    DetailCustomView pressureView;

    @BindView(R.id.daily_temperature)
    DetailCustomView temperatureView;

    @BindView(R.id.daily_description)
    TextView descriptionView;

    @BindView(R.id.daily_week_day)
    TextView weekDayView;

    @BindView(R.id.backdrop)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Daily daily = getIntent().getParcelableExtra(DAILY_EXTRA);
        UserCity userCity = getIntent().getParcelableExtra(USER_CITY);

        mIconUtil = new IconUtil(this.getResources(), getPackageName());

        DailyModel model = new DailyModel(userCity, daily);
        DailyPresenter presenter = new DailyPresenter(this, model);
        mPresenter = presenter;

        mPresenter.init();
    }

    @Override
    public void showCityName(String cityName) {
        collapsingToolbar.setTitle(cityName);
    }

    @Override
    public void showDailyData(Daily daily) {
        Glide.with(this).load(mIconUtil.getWeatherImageLargeResource(daily.getWeatherCode())).centerCrop().into(imageView);
        DateFormatter dateFormatter = new DateFormatter();

        weekDayView.setText(dateFormatter.format(daily.getDate(), "EEEE"));
        descriptionView.setText(daily.getWeatherDescription());
        temperatureView.setValue(daily.getMaxTemperature() + "° / " + daily.getMinTemperature() + "°");
        humidityView.setValue(daily.getHumidity() + " %");
        windView.setValue(daily.getWindSpeed() + " km/h   " + daily.getWindDegree() + " ° ");
        pressureView.setValue(daily.getPressure() + " hPa");

    }
}
