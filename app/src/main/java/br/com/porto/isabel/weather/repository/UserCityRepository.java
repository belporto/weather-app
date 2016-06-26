package br.com.porto.isabel.weather.repository;


import java.util.List;

import br.com.porto.isabel.weather.model.City;

public interface UserCityRepository {

    void addCity(City city);

    void removeCity(Integer id);

    City getCurrentCity();

    void selectCity(Integer id);

    List<City> getAll();
}
