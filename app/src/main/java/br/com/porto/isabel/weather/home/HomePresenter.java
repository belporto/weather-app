package br.com.porto.isabel.weather.home;


import android.util.Log;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;

public class HomePresenter implements HomeContract.PresenterContract {

    private HomeContract.ViewContract mView;
    private HomeContract.ModelContract mModel;

    public HomePresenter(HomeContract.ViewContract view, HomeContract.ModelContract model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void init() {
        mView.showProgress();
        mModel.requestCurrentData(7778677);
    }

    @Override
    public void onRequestCurrentWithSuccess(Current current) {
        mView.showCurrentData(current);
        mModel.requestDailyData(7778677);
    }

    @Override
    public void onRequestCurrentWithError() {
        //TODO:
    }

    @Override
    public void onRequestDailyWithSuccess(Forecast forecast) {
        mView.showForecast(forecast);
        mView.hideProgress();
    }

    @Override
    public void onRequestDailyWithError() {
        //TODO:
    }


}
