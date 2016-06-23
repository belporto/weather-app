package br.com.porto.isabel.weather.service;

import br.com.porto.isabel.weather.BuildConfig;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherAPI {

    private static final int NUM_OF_DAYS = 5;
    private WeatherService mService;

    public WeatherAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(WeatherService.class);
    }

    public void getDaily(int cityId, Callback<Forecast> cb) {
        Call<Forecast> call = mService.getDaily(BuildConfig.OPEN_WEATHER_MAP_API_KEY, cityId, NUM_OF_DAYS);
        call.enqueue(cb);
    }

    public void getCurrent(int cityId, Callback<Current> cb) {
        Call<Current> call = mService.getCurrent(BuildConfig.OPEN_WEATHER_MAP_API_KEY, cityId);
        call.enqueue(cb);
    }
}
