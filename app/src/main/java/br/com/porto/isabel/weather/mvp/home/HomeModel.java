package br.com.porto.isabel.weather.mvp.home;

import java.util.List;

import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.app.WeatherData;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import rx.Subscriber;

public class HomeModel implements HomeContract.ModelContract {

    private HomeContract.PresenterContract mPresenter;
    private WeatherAPI mWeatherAPI;
    private UserCityRepository mUserCityRepository;

    public HomeModel(WeatherAPI weatherAPI, UserCityRepository userCityRepository) {
        mWeatherAPI = weatherAPI;
        mUserCityRepository = userCityRepository;
    }

    @Override
    public void setPresenter(HomeContract.PresenterContract presenter) {
        mPresenter = presenter;
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
    public void requestData() {
        final UserCity currentCity = getCurrentCity();
        mWeatherAPI.getData(currentCity.getLat(), currentCity.getLon(), new Subscriber<WeatherData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPresenter.onRequestDataWithError();
            }

            @Override
            public void onNext(WeatherData weatherData) {
                weatherData.setCityName(currentCity.getName());
                mPresenter.onRequestDataWithSuccess(weatherData);
            }
        });

    }
}
