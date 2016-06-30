package br.com.porto.isabel.weather.service;

import br.com.porto.isabel.weather.model.user.CurrentInterface;
import br.com.porto.isabel.weather.model.user.ForecastInterface;

public interface WeatherAPI {

    void getDaily(double lat, double lon, WeatherAPICallback<ForecastInterface> cb);

    void getCurrent(double lat, double lon, WeatherAPICallback<CurrentInterface> cb);
}
