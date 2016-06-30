package br.com.porto.isabel.weather.mvp.home;

import java.util.List;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.service.WeatherAPICallback;

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
    public void requestDailyData() {
        UserCity currentCity = getCurrentCity();
        mWeatherAPI.getDaily(currentCity.getLat(), currentCity.getLon(), new WeatherAPICallback<Forecast>() {
            @Override
            public void onSuccess(Forecast forecast) {
                mPresenter.onRequestDailyWithSuccess(forecast);
            }

            @Override
            public void onFailure() {
                mPresenter.onRequestDailyWithError();
            }
        });
    }

    @Override
    public void requestCurrentData() {
        final UserCity currentCity = getCurrentCity();
        mWeatherAPI.getCurrent(currentCity.getLat(), currentCity.getLon(), new WeatherAPICallback<Current>() {
            @Override
            public void onSuccess(Current current) {
                current.setCityName(currentCity.getName());
                mPresenter.onRequestCurrentWithSuccess(current);
            }

            @Override
            public void onFailure() {
                mPresenter.onRequestCurrentWithError();
            }
        });
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
}
