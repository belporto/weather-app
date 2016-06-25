package br.com.porto.isabel.weather.home;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.repository.MemoryUserCityRepository;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.view.DetailCustomView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeContract.ViewContract {

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

    private static final String CURRENT = "CURRENT";

    private Current mCurrent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        Current current = null;
        if (savedInstanceState != null) {
            current = (Current) savedInstanceState.get(CURRENT);
        }

        UserCityRepository userCityRepository = MemoryUserCityRepository.getInstance();

        HomeModel model = new HomeModel(new WeatherAPI(), userCityRepository);
        mPresenter = new HomePresenter(this, model);
        model.setPresenter(mPresenter);

        if (current != null) {
            showCurrentData(current);
        } else {
            mPresenter.init();
        }

        return view;
    }

    @Override
    public void showProgress() {
        //TODO:
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showCurrentData(Current current) {
        mCurrent = current;
        cityNameTextView.setText(current.getCityName());
        weatherDescriptionTextView.setText(current.getWeatherDescription());
        String temperature = String.valueOf(current.getCurrentTemperature().intValue());
        temperatureTextView.setText(temperature + "°C");
        weatherImage.setImageResource(getWeatherImageResource(current.getWeatherCode()));
        String humidity = String.valueOf(current.getHumidity().intValue());
        humidityView.setValue(humidity + " %");
        windView.setValue(current.getWindSpeed().toString() + " km/h   " + current.getWindDegree() + " ° ");
        String pressure = String.valueOf(current.getPressure().intValue());
        pressureView.setValue(pressure + " hPa");
    }

    private
    @DrawableRes
    int getWeatherImageResource(String code) {
        String drawableName = "ic_" + code;
        @DrawableRes int drawableResourceId = this.getResources().getIdentifier(drawableName, "drawable", getActivity().getPackageName());
        return drawableResourceId;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT, mCurrent);
    }

    @Override
    public void showForecast(Forecast forecast) {

    }

}
