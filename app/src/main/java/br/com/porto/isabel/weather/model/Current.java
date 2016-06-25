package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Current {

    @SerializedName("coord")
    private Coordinator mCoordinator;

    @SerializedName("weather")
    private List<Weather> mWeatherList;

    @SerializedName("name")
    private String mCityName;

    @SerializedName("id")
    private String mCityId;

    @SerializedName("dt")
    private String mDate;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("wind")
    private Wind mWind;


    public String getCityName() {
        return mCityName;
    }

    public String getWeatherDescription() {
        Weather weather = mWeatherList.get(0);
        return weather.getDescription();
    }

    public String getWeatherCode(){
        Weather weather = mWeatherList.get(0);
        return weather.getCode();
    }

    public Double getCurrentTemperature() {
        return mMain.getTemperature();
    }

    public Double getPressure() {
        return mMain.getPressure();
    }


}
