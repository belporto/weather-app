package br.com.porto.isabel.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Isabel Porto on 22/06/2016.
 */
public class Daily {

    @SerializedName("dt")
    //TODO: formatter
    private String date;

    @SerializedName("temp")
    private Temperature temperature;

    @SerializedName("pressure")
    private Double pressure;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("speed")
    private Double windSpeed;

    @SerializedName("deg")
    private Double windDirection;

    @SerializedName("clouds")
    private Integer clouds;
}
