package br.com.porto.isabel.weather.mvp.home;

import java.util.List;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mWeatherAPI.getDaily(currentCity.getLat(), currentCity.getLon(), new Callback<Forecast>() {

            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Forecast forecast = response.body();
                    mPresenter.onRequestDailyWithSuccess(forecast);
                } else {
                    mPresenter.onRequestDailyWithError();
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                mPresenter.onRequestDailyWithError();
            }
        });
    }

    @Override
    public void requestCurrentData() {
        final UserCity currentCity = getCurrentCity();
        mWeatherAPI.getCurrent(currentCity.getLat(), currentCity.getLon(), new Callback<Current>() {
            @Override
            public void onResponse(Call<Current> call, Response<Current> response) {
                if (response.isSuccessful()) {
                    Current current = response.body();
                    current.setCityName(currentCity.getName());
                    mPresenter.onRequestCurrentWithSuccess(current);
                } else {
                    mPresenter.onRequestCurrentWithError();
                }
            }

            @Override
            public void onFailure(Call<Current> call, Throwable t) {
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
