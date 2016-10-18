package br.com.porto.isabel.weather.mvp.home.builder;


import javax.inject.Singleton;

import br.com.porto.isabel.weather.app.builder.module.WeatherComponent;
import br.com.porto.isabel.weather.mvp.home.HomeFragment;
import dagger.Component;

@HomeFragmentScope
@Component(modules = {HomeFragmentModule.class}, dependencies = {WeatherComponent.class})
public interface HomeFragmentComponent {

    void inject(HomeFragment fragment);

}
