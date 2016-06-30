package br.com.porto.isabel.weather.service;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;

public interface WeatherAPI {

    void getDaily(double lat, double lon, WeatherAPICallback<Forecast> cb);

    void getCurrent(double lat, double lon, WeatherAPICallback<Current> cb);
}
