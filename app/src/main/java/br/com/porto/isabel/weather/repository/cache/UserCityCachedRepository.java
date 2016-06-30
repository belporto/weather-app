package br.com.porto.isabel.weather.repository.cache;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.repository.cache.UserCityCacheStrategy;

public class UserCityCachedRepository implements UserCityRepository {


    private Map<String, UserCity> mCities;
    private UserCity mCurrent;
    private Gson mGson;
    private UserCityCacheStrategy mCacheStrategy;

    public UserCityCachedRepository(UserCityCacheStrategy cacheStrategy, Gson gson) {
        mCacheStrategy = cacheStrategy;
        mCities = mCacheStrategy.getCities();
        mCurrent = mCacheStrategy.getCurrent();
        mGson = gson;

        if (mCities == null) {
            setupDefaultCities();
            mCurrent = getFirstElement();
            mCacheStrategy.saveCurrentCity(mCurrent);
        }
    }

    private UserCity getFirstElement() {
        return new ArrayList<>(mCities.values()).get(0);
    }

    @Override
    public void addCity(UserCity city) {
        mCities.put(city.getId(), city);
        mCacheStrategy.saveCities(mCities);
    }

    @Override
    public void removeCity(String id) {
        mCities.remove(id);
        if (mCurrent.getId().equals(id)) {
            if (!mCities.isEmpty()) {
                mCurrent = getFirstElement();
                mCacheStrategy.saveCurrentCity(mCurrent);
            }
        }
        mCacheStrategy.saveCities(mCities);
    }

    @Override
    public UserCity getCurrentCity() {
        return mCurrent;
    }

    @Override
    public void selectCity(String id) {
        mCurrent = mCities.get(id);
        mCacheStrategy.saveCurrentCity(mCurrent);
    }

    @Override
    public List<UserCity> getAll() {
        return new ArrayList(mCities.values());
    }


    private void setupDefaultCities() {
        String defaultCities = "{\"ChIJL6wn6oAOZ0gRoHExl6nHAAo\":{\"mId\":\"ChIJL6wn6oAOZ0gRoHExl6nHAAo\",\"mLat\":53.3498053,\"mLon\":-6.2603097,\"mName\":\"Dublin\"},\"ChIJ5TCOcRaYpBIRCmZHTz37sEQ\":{\"mId\":\"ChIJ5TCOcRaYpBIRCmZHTz37sEQ\",\"mLat\":41.38506389999999,\"mLon\":2.1734035,\"mName\":\"Barcelona\"},\"ChIJOwg_06VPwokRYv534QaPC8g\":{\"mId\":\"ChIJOwg_06VPwokRYv534QaPC8g\",\"mLat\":40.712783699999996,\"mLon\":-74.0059413,\"mName\":\"New York\"},\"ChIJdd4hrwug2EcRmSrV3Vo6llI\":{\"mId\":\"ChIJdd4hrwug2EcRmSrV3Vo6llI\",\"mLat\":51.5073509,\"mLon\":-0.1277583,\"mName\":\"London\"}}";
        mCities = getFromJSON(defaultCities);
        mCacheStrategy.saveCities(mCities);
    }

    private Map<String, UserCity> getFromJSON(String json) {
        Map<String, UserCity> cities = null;
        Type type = new TypeToken<Map<String, UserCity>>() {
        }.getType();
        if (json != null) {
            cities = mGson.fromJson(json, type);
        }
        return cities;
    }


}
