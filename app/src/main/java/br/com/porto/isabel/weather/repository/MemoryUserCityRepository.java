package br.com.porto.isabel.weather.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.porto.isabel.weather.model.user.UserCity;

public class MemoryUserCityRepository implements UserCityRepository {

    private Map<String, UserCity> mCities;
    private UserCity mCurrent;

    public static MemoryUserCityRepository instance;

    public static MemoryUserCityRepository getInstance() {
        if (instance == null) {
            instance = new MemoryUserCityRepository();
        }
        return instance;
    }

    private MemoryUserCityRepository() {
        mCities = new HashMap<>();

        UserCity city = new UserCity("Dublin", 53.3498053, -6.2603097, "ChIJL6wn6oAOZ0gRoHExl6nHAAo");
        mCities.put(city.getId(), city);
    }

    @Override
    public void addCity(UserCity city) {
        mCities.put(city.getId(), city);
    }

    @Override
    public void removeCity(String id) {
        mCities.remove(id);
    }

    @Override
    public UserCity getCurrentCity() {
        return mCurrent;
    }

    @Override
    public void selectCity(String id) {
        mCurrent = mCities.get(id);
    }

    @Override
    public List<UserCity> getAll() {
        return new ArrayList(mCities.values());
    }
}
