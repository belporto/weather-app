package br.com.porto.isabel.weather.service;

import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import rx.Subscriber;

public interface WeatherAPI {

    void getDaily(double lat, double lon, Subscriber<ForecastInterface> subscriber);

    void getCurrent(double lat, double lon, Subscriber<CurrentInterface> subscriber);
}
