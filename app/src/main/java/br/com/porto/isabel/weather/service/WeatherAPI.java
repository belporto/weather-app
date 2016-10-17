package br.com.porto.isabel.weather.service;

import br.com.porto.isabel.weather.model.app.WeatherData;
import rx.Observable;
import rx.Subscriber;

public interface WeatherAPI {

    Observable<WeatherData> getData(double lat, double lon);

}
