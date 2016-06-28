package br.com.porto.isabel.weather.home;


import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;

public class HomePresenter implements HomeContract.PresenterContract {

    private HomeContract.ViewContract mView;
    private HomeContract.ModelContract mModel;
    private boolean mSwipe;

    public HomePresenter(HomeContract.ViewContract view, HomeContract.ModelContract model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onRequestCurrentWithSuccess(Current current) {
        mView.showCurrentData(current);
        mModel.requestDailyData();
    }

    @Override
    public void onRequestCurrentWithError() {
        mView.showError();
    }

    @Override
    public void onRefresh() {
        mSwipe = true;
        mModel.requestCurrentData();
    }

    @Override
    public void onCitySelected(UserCity city) {
        UserCity currentCity = mModel.getCurrentCity();
        boolean isDifferentCity = currentCity != null && city != null && !currentCity.equals(city);
        if (isDifferentCity) {
            mView.showProgress();
            mModel.selectCity(city);
            mModel.requestCurrentData();
        }

    }

    @Override
    public void onCreateOptionsMenu() {
        mView.showCityList(mModel.getUserCityList(), mModel.getCurrentCity());
    }

    @Override
    public void onDailySelected(Daily daily) {
        mView.showDailyInformation(daily, mModel.getCurrentCity());
    }

    @Override
    public void init() {
        mView.showProgress();
        mModel.requestCurrentData();
    }

    @Override
    public void onRequestDailyWithSuccess(Forecast forecast) {
        mView.showForecast(forecast);
        if (mSwipe) {
            mView.hideSwipe();
        } else {
            mView.hideProgress();
        }
    }

    @Override
    public void onRequestDailyWithError() {
        mView.showError();
    }


}
