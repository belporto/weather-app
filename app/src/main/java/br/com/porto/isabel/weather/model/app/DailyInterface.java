package br.com.porto.isabel.weather.model.app;

import android.os.Parcelable;

import java.util.Date;


public interface DailyInterface extends Parcelable {
    int getMinTemperature();

    int getMaxTemperature();

    Date getDate();

    int getHumidity();

    String getWeatherCode();

    int getWindSpeed();

    int getWindDegree();

    int getPressure();

    String getWeatherDescription();
}
