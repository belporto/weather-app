package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

public class Coordinator {

    @SerializedName("lat")
    private Double mLat;

    @SerializedName("lon")
    private Double mLong;

}
