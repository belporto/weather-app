package br.com.porto.isabel.weather.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.porto.isabel.weather.model.user.UserCity;

public class SharedPreferencesUserCityRepository implements UserCityRepository {

    private static final String USER_CITY_SHARED_PREFERENCES = "USER_CITY_SHARED_PREFERENCES";
    private static final String USER_CITIES_JSON = "USER_CITIES_JSON";
    private static final String CURRENT_CITY = "CURRENT_CITY";
    private Map<String, UserCity> mCities;
    private UserCity mCurrent;

    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    public SharedPreferencesUserCityRepository(Context context, Gson gson) {
        mSharedPreferences = context.getSharedPreferences(USER_CITY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mGson = gson;
        mCities = getCities();
        mCurrent = getCurrent();

        if (mCities == null) {
            setupDefaultCities();
            mCurrent = getFirstElement();
            saveCurrentCity(mCurrent);
        }
    }

    private UserCity getFirstElement() {
        return new ArrayList<>(mCities.values()).get(0);
    }

    @Override
    public void addCity(UserCity city) {
        mCities.put(city.getId(), city);
        saveCities(mCities);
    }

    @Override
    public void removeCity(String id) {
        mCities.remove(id);
        if (mCurrent.getId().equals(id)) {
            if (!mCities.isEmpty()) {
                mCurrent = getFirstElement();
                saveCurrentCity(mCurrent);
            }
        }
        saveCities(mCities);
    }

    @Override
    public UserCity getCurrentCity() {
        return mCurrent;
    }

    @Override
    public void selectCity(String id) {
        mCurrent = mCities.get(id);
        saveCurrentCity(mCurrent);
    }

    @Override
    public List<UserCity> getAll() {
        return new ArrayList(mCities.values());
    }

    private void saveCurrentCity(UserCity city) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String json = mGson.toJson(city);
        editor.putString(CURRENT_CITY, json);
        editor.commit();
    }

    private void setupDefaultCities() {
        String defaultCities = "{\"ChIJL6wn6oAOZ0gRoHExl6nHAAo\":{\"mId\":\"ChIJL6wn6oAOZ0gRoHExl6nHAAo\",\"mLat\":53.3498053,\"mLon\":-6.2603097,\"mName\":\"Dublin\"},\"ChIJ5TCOcRaYpBIRCmZHTz37sEQ\":{\"mId\":\"ChIJ5TCOcRaYpBIRCmZHTz37sEQ\",\"mLat\":41.38506389999999,\"mLon\":2.1734035,\"mName\":\"Barcelona\"},\"ChIJOwg_06VPwokRYv534QaPC8g\":{\"mId\":\"ChIJOwg_06VPwokRYv534QaPC8g\",\"mLat\":40.712783699999996,\"mLon\":-74.0059413,\"mName\":\"New York\"},\"ChIJdd4hrwug2EcRmSrV3Vo6llI\":{\"mId\":\"ChIJdd4hrwug2EcRmSrV3Vo6llI\",\"mLat\":51.5073509,\"mLon\":-0.1277583,\"mName\":\"London\"}}";
        mCities = getFromJSON(defaultCities);
        saveCities(mCities);
    }

    private void saveCities(Map<String, UserCity> cities) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String json = mGson.toJson(cities);
        editor.putString(USER_CITIES_JSON, json);
        editor.commit();
    }

    private Map<String, UserCity> getCities() {
        String json = mSharedPreferences.getString(USER_CITIES_JSON, null);
        return getFromJSON(json);
    }

    private UserCity getCurrent() {
        String json = mSharedPreferences.getString(CURRENT_CITY, null);
        if (json != null) {
            return mGson.fromJson(json, UserCity.class);
        } else {
            return null;
        }
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
