package br.com.porto.isabel.weather.app.builder;

import android.content.Context;

import br.com.porto.isabel.weather.app.builder.module.DaggerWeatherComponent;
import br.com.porto.isabel.weather.app.builder.module.NetworkModule;
import br.com.porto.isabel.weather.app.builder.module.RepositoryModule;
import br.com.porto.isabel.weather.app.builder.module.RxModule;
import br.com.porto.isabel.weather.app.builder.module.WeatherComponent;
import br.com.porto.isabel.weather.app.builder.module.WeatherModule;
import br.com.porto.isabel.weather.mvp.home.HomeFragment;
import br.com.porto.isabel.weather.mvp.home.builder.DaggerHomeFragmentComponent;

/**
 * Created by isabelporto on 18/10/2016.
 */

public class DefaultWeatherInjector implements WeatherInjector {

    private Context context;
    private WeatherComponent component;

    public DefaultWeatherInjector(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public WeatherComponent getComponent() {
        if (component == null) {
            //Lazy init for startup performance
            component = buildWeatherComponent(context.getApplicationContext());
        }
        return component;
    }

    private WeatherComponent buildWeatherComponent(Context applicationContext) {
        return DaggerWeatherComponent.builder()
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .rxModule(new RxModule())
                .weatherModule(new WeatherModule(applicationContext))
                .build();
    }


    @Override
    public void inject(HomeFragment fragment) {
        DaggerHomeFragmentComponent.builder()
                .weatherComponent(getComponent())
                .build().inject(fragment);
    }
}
