package br.com.porto.isabel.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Isabel Porto on 22/06/2016.
 */
public class Weather {

    @SerializedName("id")
    private Integer id;

    @SerializedName("main")
    private String main;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;
}
