package br.com.porto.isabel.weather.configuration;


import br.com.porto.isabel.weather.model.City;

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
    public void onSwipe(City city) {
        mModel.deleteCity(city);
        mView.onCityDeleted(city);
    }
}
