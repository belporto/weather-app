package br.com.porto.isabel.weather.mvp.home;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.app.WeatherData;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import rx.Observable;

public class HomeModel implements HomeContract.ModelContract {

    private WeatherAPI mWeatherAPI;
    private UserCityRepository mUserCityRepository;

    public HomeModel(WeatherAPI weatherAPI, UserCityRepository userCityRepository) {
        mWeatherAPI = weatherAPI;
        mUserCityRepository = userCityRepository;
    }

    @Override
    public List<UserCity> getUserCityList() {
        return mUserCityRepository.getAll();
    }

    @Override
    public void selectCity(UserCity city) {
        mUserCityRepository.selectCity(city.getId());
    }


    @Override
    public UserCity getCurrentCity() {
        return mUserCityRepository.getCurrentCity();
    }

    @Override
    public Observable<WeatherData> requestData() {
        final UserCity currentCity = getCurrentCity();
        return mWeatherAPI.getData(currentCity.getLat(), currentCity.getLon()).delay(10, TimeUnit.SECONDS);

    }
}
