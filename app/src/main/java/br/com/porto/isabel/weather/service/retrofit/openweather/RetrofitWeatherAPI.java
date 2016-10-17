package br.com.porto.isabel.weather.service.retrofit.openweather;

import br.com.porto.isabel.weather.BuildConfig;
import br.com.porto.isabel.weather.model.app.WeatherData;
import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import br.com.porto.isabel.weather.service.WeatherAPI;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
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
    public void getData(double lat, double lon, Subscriber<WeatherData> subscriber) {
        Observable<Forecast> dailyObservable = mService.getDaily(BuildConfig.OPEN_WEATHER_MAP_API_KEY, lat, lon, NUM_OF_DAYS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Current> currentObservable =  mService.getCurrent(BuildConfig.OPEN_WEATHER_MAP_API_KEY, lat, lon).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        Observable<WeatherData> combined = Observable.zip(dailyObservable, currentObservable, new Func2<Forecast, Current, WeatherData>() {
            @Override
            public WeatherData call(Forecast forecast, Current current) {
                return new WeatherData(current, forecast);
            }
        });

        combined.subscribe(new Subscriber<WeatherData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(WeatherData weatherData) {
                subscriber.onNext(weatherData);
            }
        });


    }

}

