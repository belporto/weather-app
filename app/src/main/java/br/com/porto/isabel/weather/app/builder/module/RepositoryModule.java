package br.com.porto.isabel.weather.app.builder.module;


import android.content.Context;

import com.google.gson.Gson;

import br.com.porto.isabel.weather.app.builder.WeatherScope;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.repository.cache.SharedPreferencesUserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCachedRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.service.retrofit.openweather.RetrofitWeatherAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by isabelporto on 17/10/2016.
 */


@Module(includes = {WeatherModule.class})
public class RepositoryModule {

    @Provides
    @WeatherScope
    Gson providesGson(){
        return new Gson();
    }

    @Provides
    @WeatherScope
    UserCityCacheStrategy providesUserCityCacheStrategy(Context context, Gson gson){
        return new SharedPreferencesUserCityCacheStrategy(context, gson);
    }

    @Provides
    @WeatherScope
    UserCityRepository providesUserCityRepository(UserCityCacheStrategy cacheStrategy, Gson gson){
        return new UserCityCachedRepository(cacheStrategy, gson);
    }


}
