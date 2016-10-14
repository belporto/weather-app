package br.com.porto.isabel.weather.mvp.home;

import java.util.List;

import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
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
    public void requestDailyData() {
        UserCity currentCity = getCurrentCity();
        mWeatherAPI.getDaily(currentCity.getLat(), currentCity.getLon(), new Subscriber<ForecastInterface>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPresenter.onRequestDailyWithError();
            }

            @Override
            public void onNext(ForecastInterface forecast) {
                mPresenter.onRequestDailyWithSuccess(forecast);
            }
        });
    }

    @Override
    public void requestCurrentData() {
        final UserCity currentCity = getCurrentCity();
        mWeatherAPI.getCurrent(currentCity.getLat(), currentCity.getLon(), new Subscriber<CurrentInterface>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPresenter.onRequestCurrentWithError();
            }

            @Override
            public void onNext(CurrentInterface current) {
                current.setCityName(currentCity.getName());
                mPresenter.onRequestCurrentWithSuccess(current);
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
