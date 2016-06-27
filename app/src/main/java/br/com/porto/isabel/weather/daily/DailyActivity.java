package br.com.porto.isabel.weather.daily;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.IconUtil;
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
        model.setPresenter(presenter);
        mPresenter = presenter;

        mPresenter.init();
    }

    @Override
    public void showCityName(String cityName) {
        collapsingToolbar.setTitle(cityName);
    }

    @Override
    public void showDailyData(Daily daily) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(mIconUtil.getWeatherImageLargeResource(daily.getWeatherCode())).centerCrop().into(imageView);
    }
}
