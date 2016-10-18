package br.com.porto.isabel.weather.app.builder.module;

import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.DefaultAndroidRxSchedulers;

import br.com.porto.isabel.weather.app.builder.WeatherScope;
import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

  @Provides
  @WeatherScope
  public AndroidRxSchedulers androidRxSchedulers() {
    return new DefaultAndroidRxSchedulers();
  }
}
