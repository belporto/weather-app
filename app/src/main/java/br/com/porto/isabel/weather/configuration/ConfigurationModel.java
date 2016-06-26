package br.com.porto.isabel.weather.configuration;

import java.util.List;

import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;

public class ConfigurationModel implements ConfigurationContract.ModelContract {

    private UserCityRepository mUserCityRepository;

    public ConfigurationModel(UserCityRepository userCityRepository) {
        mUserCityRepository = userCityRepository;
    }

    @Override
    public List<UserCity> getUserCityList() {
        return mUserCityRepository.getAll();
    }

    @Override
    public void deleteCity(UserCity city) {
        mUserCityRepository.removeCity(city.getId());
    }


    @Override
    public void addCity(UserCity city) {
        mUserCityRepository.addCity(city);
    }
}
