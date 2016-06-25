package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private Double mTemperature;

    @SerializedName("humidity")
    private Double mHumidity;

    @SerializedName("pressure")
    private Double mPressure;

    @SerializedName("temp_min")
    private Double mTempMin;

    @SerializedName("temp_max")
    private Double mTempMax;


    public Double getTemperature() {
        return mTemperature;
    }

    public Double getPressure() {
        return mPressure;
    }
}
