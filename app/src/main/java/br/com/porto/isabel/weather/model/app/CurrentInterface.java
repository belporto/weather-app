package br.com.porto.isabel.weather.model.app;


import android.os.Parcelable;

public interface CurrentInterface extends Parcelable {
    String getCityName();

    String getWeatherDescription();

    String getWeatherCode();

    int getCurrentTemperature();

    int getPressure();

    int getHumidity();

    int getWindSpeed();

    int getWindDegree();

    void setCityName(String name);
}
