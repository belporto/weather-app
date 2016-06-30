package br.com.porto.isabel.weather.service;

public interface WeatherAPICallback<T> {

    void onSuccess(T object);

    void onFailure();
}
