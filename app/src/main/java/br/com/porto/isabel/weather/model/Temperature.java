package br.com.porto.isabel.weather.model;

import com.google.gson.annotations.SerializedName;


public class Temperature {

    @SerializedName("day")
    private Double day;

    @SerializedName("min")
    private Double min;

    @SerializedName("max")
    private Double max;

    @SerializedName("night")
    private Double night;

    @SerializedName("eve")
    private Double eve;

    @SerializedName("morn")
    private Double morning;

}