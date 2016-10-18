package br.com.porto.isabel.weather.app.builder.module;

import android.content.Context;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import br.com.porto.isabel.weather.app.builder.WeatherScope;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import dagger.Component;

@WeatherScope
@Component(modules = {NetworkModule.class, RepositoryModule.class, RxModule.class, WeatherModule.class})
public interface WeatherComponent {

    Context context();

    UserCityRepository userCityRepository();

    WeatherAPI weatherAPI();

    AndroidRxSchedulers androidRxSchedulers();
}