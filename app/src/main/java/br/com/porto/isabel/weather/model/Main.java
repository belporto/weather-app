package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private Double temperature;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("pressure")
    private Integer pressure;

    @SerializedName("temp_min")
    private Double tempMin;

    @SerializedName("temp_max")
    private Double tempMax;
}
