package br.com.porto.isabel.weather.app.builder.module;


import android.content.Context;

import br.com.porto.isabel.weather.app.builder.WeatherScope;
import br.com.porto.isabel.weather.model.openweather.Weather;
import br.com.porto.isabel.weather.service.WeatherAPI;
import br.com.porto.isabel.weather.service.retrofit.openweather.RetrofitWeatherAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by isabelporto on 17/10/2016.
 */


@Module
public class WeatherModule {

    private Context mContext;

    public WeatherModule(Context context){
        mContext = context;
    }

    @Provides
    @WeatherScope
    Context providesContext(){
        return mContext;
    }


}
