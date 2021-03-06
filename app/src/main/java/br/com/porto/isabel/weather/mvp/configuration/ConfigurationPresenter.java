package br.com.porto.isabel.weather.mvp.configuration;


import br.com.porto.isabel.weather.model.app.UserCity;

public class ConfigurationPresenter implements ConfigurationContract.PresenterContract {

    private ConfigurationContract.ViewContract mView;
    private ConfigurationContract.ModelContract mModel;

    public ConfigurationPresenter(ConfigurationContract.ViewContract view, ConfigurationContract.ModelContract model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void init() {
        mView.showUserCity(mModel.getUserCityList());
    }

    @Override
    public void onAddCityClicked() {
        mView.showCityDialog();
    }

    @Override
    public void onCityClicked(UserCity city) {
        mModel.addCity(city);
        mView.addCity(city);
    }

    @Override
    public void onCitySizeLimitReached() {
        mView.revertSwipe();
        mView.showLimitDialog();
    }

    @Override
    public void onCityDeleted(UserCity city) {
        mView.deleteCity(city);
    }

    @Override
    public void onSwipe(UserCity city) {
        mModel.deleteCity(city);
    }

    @Override
    public void onCitySelected(UserCity userCity) {
        mModel.selectCity(userCity);
        mView.showCityInformation(userCity);
    }
}
