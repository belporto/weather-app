package br.com.porto.isabel.weather.mvp.home.builder;

import android.content.Context;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import br.com.porto.isabel.weather.mvp.home.HomeContract;
import br.com.porto.isabel.weather.mvp.home.HomeModel;
import br.com.porto.isabel.weather.mvp.home.HomePresenter;
import br.com.porto.isabel.weather.mvp.home.HomeView;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by isabelporto on 18/10/2016.
 */


@Module
public class HomeFragmentModule {

    @Provides
    @HomeFragmentScope
    public HomeContract.ModelContract provideModule(UserCityRepository userCityRepository, WeatherAPI weatherAPI) {
        return new HomeModel(userCityRepository, weatherAPI);
    }

    @Provides
    @HomeFragmentScope
    public HomeContract.ViewContract provideView(Context context) {
        return new HomeView(context);
    }

    @Provides
    @HomeFragmentScope
    public HomeContract.PresenterContract providePresenter(HomeContract.ViewContract view, HomeContract.ModelContract model, AndroidRxSchedulers schedulers) {
        return new HomePresenter(view, model, schedulers);
    }
}
