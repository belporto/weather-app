package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    @SerializedName("city")
    private City city;

    @SerializedName("list")
    private List<Daily> dailyList;

}
