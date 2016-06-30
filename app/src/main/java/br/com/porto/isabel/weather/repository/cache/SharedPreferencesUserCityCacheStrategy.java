package br.com.porto.isabel.weather.repository.cache;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import br.com.porto.isabel.weather.model.user.UserCity;

public class SharedPreferencesUserCityCacheStrategy implements UserCityCacheStrategy {
    private static final String USER_CITY_SHARED_PREFERENCES = "USER_CITY_SHARED_PREFERENCES";
    private static final String USER_CITIES_JSON = "USER_CITIES_JSON";
    private static final String CURRENT_CITY = "CURRENT_CITY";

    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    public SharedPreferencesUserCityCacheStrategy(Context context, Gson gson) {
        mSharedPreferences = context.getSharedPreferences(USER_CITY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mGson = gson;
    }

    @Override
    public void saveCurrentCity(UserCity city) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String json = mGson.toJson(city);
        editor.putString(CURRENT_CITY, json);
        editor.commit();
    }


    @Override
    public void saveCities(Map<String, UserCity> cities) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String json = mGson.toJson(cities);
        editor.putString(USER_CITIES_JSON, json);
        editor.commit();
    }

    @Override
    public Map<String, UserCity> getCities() {
        String json = mSharedPreferences.getString(USER_CITIES_JSON, null);
        return getFromJSON(json);
    }

    @Override
    public UserCity getCurrent() {
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
