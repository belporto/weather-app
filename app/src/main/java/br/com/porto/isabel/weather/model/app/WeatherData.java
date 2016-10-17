package br.com.porto.isabel.weather.model.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isabelporto on 14/10/2016.
 */

public class WeatherData implements Parcelable {

    private CurrentInterface current;
    private ForecastInterface forecast;

    public WeatherData(CurrentInterface current, ForecastInterface forecast) {
        this.current = current;
        this.forecast = forecast;
    }

    protected WeatherData(Parcel in) {
        current = in.readParcelable(CurrentInterface.class.getClassLoader());
        forecast = in.readParcelable(ForecastInterface.class.getClassLoader());
    }

    public void setCityName(String cityName){
        current.setCityName(cityName);
    }

    public ForecastInterface getForecast() {
        return forecast;
    }

    public CurrentInterface getCurrent() {
        return current;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(current, flags);
        dest.writeParcelable(forecast, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };
}
