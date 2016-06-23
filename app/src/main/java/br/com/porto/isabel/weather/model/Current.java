package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Current {

    @SerializedName("coord")
    private Coordinator coordinator;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("name")
    private String cityName;

    @SerializedName("id")
    private String cityId;

    @SerializedName("dt")
    private String date;

    @SerializedName("main")
    private Main main;


}
