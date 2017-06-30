package br.com.porto.isabel.weather.service.retrofit.openweather;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.service.WeatherAPICallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitWeatherAPI implements WeatherAPI {

    private static final int NUM_OF_DAYS = 5;
    private WeatherService mService;
    private Context mContext;

    public RetrofitWeatherAPI(Context context) {
        mContext = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(WeatherService.class);
    }

    @Override
    public void getDaily(double lat, double lon, final WeatherAPICallback<ForecastInterface> cb) {
        Callback<Forecast> callback = new RetrofitCallback<>(cb);

        Call<Forecast> call = mService.getDaily(getApiKey(), lat, lon, NUM_OF_DAYS);
        call.enqueue(callback);
    }

    @Override
    public void getCurrent(double lat, double lon, final WeatherAPICallback<CurrentInterface> cb) {

        Callback<Current> callback = new RetrofitCallback<>(cb);

        Call<Current> call = mService.getCurrent(getApiKey(), lat, lon);
        call.enqueue(callback);
    }

    public class RetrofitCallback<T, Y extends T> implements Callback<Y> {
        private WeatherAPICallback<T> mCb;

        public RetrofitCallback(WeatherAPICallback<T> cb) {
            mCb = cb;
        }

        @Override
        public void onResponse(Call<Y> call, Response<Y> response) {
            if (response.isSuccessful()) {
                T object = response.body();
                mCb.onSuccess(object);
            } else {
                mCb.onFailure();
            }
        }

        @Override
        public void onFailure(Call<Y> call, Throwable t) {
            mCb.onFailure();
        }
    }

    private String getApiKey() {
        String apiKey = "";
        try {
            ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(
                    mContext.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiKey = bundle.getString("OPEN_WEATHER_KEY");
        } catch (Exception e) {
            //TODO:
        }

        return apiKey;
    }
}

