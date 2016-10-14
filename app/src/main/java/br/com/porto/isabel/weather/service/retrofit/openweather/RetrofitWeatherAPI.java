package br.com.porto.isabel.weather.service.retrofit.openweather;

import android.util.Log;

import br.com.porto.isabel.weather.BuildConfig;
import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.service.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RetrofitWeatherAPI implements WeatherAPI {

    private static final int NUM_OF_DAYS = 5;
    private WeatherService mService;

    public RetrofitWeatherAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(WeatherService.class);
    }

    @Override
    public void getDaily(double lat, double lon, Subscriber<ForecastInterface> subscriber) {

        mService.getDaily(BuildConfig.OPEN_WEATHER_MAP_API_KEY, lat, lon, NUM_OF_DAYS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Forecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Forecast forecast) {
                        subscriber.onNext(forecast);
                    }
                });
    }

    @Override
    public void getCurrent(double lat, double lon, final Subscriber<CurrentInterface> subscriber) {

        mService.getCurrent(BuildConfig.OPEN_WEATHER_MAP_API_KEY, lat, lon).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CurrentInterface>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(CurrentInterface current) {
                        subscriber.onNext(current);
                    }
                });
    }

}

