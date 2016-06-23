package br.com.porto.isabel.weather.repository;


import java.util.HashMap;
import java.util.Map;

import br.com.porto.isabel.weather.model.City;

public class MemoryUserCityRepository implements UserCityRepository {

    private Map<Integer, City> mCities;
    private City mCurrent;

    public static MemoryUserCityRepository instance;

    public static MemoryUserCityRepository getInstance(){
        if(instance == null){
            instance = new MemoryUserCityRepository();
        }
        return instance;
    }

    private MemoryUserCityRepository() {
        mCities = new HashMap<>();
        City city = new City();
        city.setId(2964574);
        mCurrent = city;
        mCities.put(city.getId(), city);
    }

    @Override
    public void addCity(City city) {
        mCities.put(city.getId(), city);
    }

    @Override
    public void removeCity(Integer id) {
        mCities.remove(id);
    }

    @Override
    public City getCurrentCity() {
        return mCurrent;
    }

    @Override
    public void selectCity(Integer id) {
        mCurrent = mCities.get(id);
    }
}
