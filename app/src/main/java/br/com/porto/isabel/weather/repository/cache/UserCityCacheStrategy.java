package br.com.porto.isabel.weather.repository.cache;


import java.util.Map;

import br.com.porto.isabel.weather.model.app.UserCity;

public interface UserCityCacheStrategy {
    void saveCurrentCity(UserCity city);

    void saveCities(Map<String, UserCity> cities);

    Map<String, UserCity> getCities();

    UserCity getCurrent();
}
