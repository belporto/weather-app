package br.com.porto.isabel.weather;

import android.app.Application;

import br.com.porto.isabel.weather.app.builder.DefaultWeatherInjector;

/**
 * Created by isabelporto on 18/10/2016.
 */

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WeatherAppGraph.init(new DefaultWeatherInjector(this));

    }
}
