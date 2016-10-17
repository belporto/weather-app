package br.com.porto.isabel.weather.mvp.home;


import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.app.WeatherData;

public class HomePresenter implements HomeContract.PresenterContract {

    private HomeContract.ViewContract mView;
    private HomeContract.ModelContract mModel;
    private boolean mSwipe;

    public HomePresenter(HomeContract.ViewContract view, HomeContract.ModelContract model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void init() {
        mView.showProgress();
        mModel.requestData();
    }

    @Override
    public void onTryAgainClicked() {
        mView.showProgress();
        mModel.requestData();
    }

    @Override
    public void onRefresh() {
        mSwipe = true;
        mModel.requestData();
    }

    @Override
    public void onCitySelected(UserCity city) {
        UserCity currentCity = mModel.getCurrentCity();
        boolean isDifferentCity = currentCity != null && city != null && !currentCity.equals(city);
        if (isDifferentCity) {
            mView.showProgress();
            mModel.selectCity(city);
            mModel.requestData();
        }
    }

    @Override
    public void onRequestDataWithSuccess(WeatherData weatherData) {
        mView.showCurrentData(weatherData.getCurrent());
        mView.showForecast(weatherData.getForecast());
        mView.showContent();
        hideSwipe();
    }

    @Override
    public void onRequestDataWithError() {
        mView.showError();
        hideSwipe();
    }

    @Override
    public void onCreateOptionsMenu() {
        mView.showCityList(mModel.getUserCityList(), mModel.getCurrentCity());
    }

    @Override
    public void onDailySelected(DailyInterface daily) {
        mView.showDailyInformation(daily, mModel.getCurrentCity());
    }

    private void hideSwipe() {
        if (mSwipe) {
            mSwipe = false;
            mView.hideSwipe();
        }
    }

}
