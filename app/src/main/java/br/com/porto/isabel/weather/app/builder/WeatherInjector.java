package br.com.porto.isabel.weather.app.builder;

import br.com.porto.isabel.weather.app.builder.module.WeatherComponent;
import br.com.porto.isabel.weather.mvp.home.HomeFragment;

/**
 * Created by isabelporto on 18/10/2016.
 */
public interface WeatherInjector {
    WeatherComponent getComponent();

    void inject(HomeFragment fragment);
}
