package br.com.porto.isabel.weather.model;

import com.google.gson.annotations.SerializedName;


public class Wind {

    @SerializedName("speed")
    private Double speed;

    @SerializedName("deg")
    private Double degree;
}
