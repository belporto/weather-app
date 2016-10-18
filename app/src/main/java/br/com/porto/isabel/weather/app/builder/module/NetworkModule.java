package br.com.porto.isabel.weather.app.builder.module;


import br.com.porto.isabel.weather.app.builder.WeatherScope;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.service.retrofit.openweather.RetrofitWeatherAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by isabelporto on 17/10/2016.
 */


@Module
public class NetworkModule {

    @Provides
    @WeatherScope
    WeatherAPI providesWeatherAPI(){
        return new RetrofitWeatherAPI();
    }


}
