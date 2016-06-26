package br.com.porto.isabel.weather.configuration;

import java.util.List;

import br.com.porto.isabel.weather.model.City;
import br.com.porto.isabel.weather.repository.UserCityRepository;

public class ConfigurationModel implements ConfigurationContract.ModelContract {

    private ConfigurationContract.PresenterContract mPresenter;
    private UserCityRepository mUserCityRepository;

    public ConfigurationModel(UserCityRepository userCityRepository) {
        mUserCityRepository = userCityRepository;
    }

    @Override
    public void setPresenter(ConfigurationContract.PresenterContract presenter) {
        mPresenter = presenter;
    }

    @Override
    public List<City> getUserCityList() {
        return mUserCityRepository.getAll();
    }
}