package br.com.porto.isabel.weather.home;

import br.com.porto.isabel.weather.model.City;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
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
        City currentCity = getCurrentCity();
        mWeatherAPI.getDaily(currentCity.getId(), new Callback<Forecast>() {

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
        City currentCity = getCurrentCity();
        mWeatherAPI.getCurrent(currentCity.getId(), new Callback<Current>() {
            @Override
            public void onResponse(Call<Current> call, Response<Current> response) {
                if (response.isSuccessful()) {
                    Current current = response.body();
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


    private City getCurrentCity() {
        return mUserCityRepository.getCurrentCity();
    }
}
