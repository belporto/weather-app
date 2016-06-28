package br.com.porto.isabel.weather.mvp.configuration;

import java.util.List;

import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;

public class ConfigurationModel implements ConfigurationContract.ModelContract {

    private ConfigurationContract.PresenterContract mPresenter;
    private UserCityRepository mUserCityRepository;

    public ConfigurationModel(UserCityRepository userCityRepository) {
        mUserCityRepository = userCityRepository;

    }

    public void setPresenter(ConfigurationContract.PresenterContract presenter) {
        mPresenter = presenter;
    }

    @Override
    public List<UserCity> getUserCityList() {
        return mUserCityRepository.getAll();
    }

    @Override
    public void deleteCity(UserCity city) {
        if (mUserCityRepository.getAll().size() > 1) {
            mUserCityRepository.removeCity(city.getId());
            mPresenter.onCityDeleted(city);
        } else {
            mPresenter.onCitySizeLimitReached();
        }
    }


    @Override
    public void addCity(UserCity city) {
        mUserCityRepository.addCity(city);
    }
}
