package br.com.porto.isabel.weather.repository;


import java.util.List;

import br.com.porto.isabel.weather.model.app.UserCity;

public interface UserCityRepository {

    void addCity(UserCity city);

    void removeCity(String id);

    UserCity getCurrentCity();

    void selectCity(String id);

    List<UserCity> getAll();
}
