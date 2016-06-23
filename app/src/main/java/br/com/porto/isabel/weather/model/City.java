package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("population")
    private Integer population;

    @SerializedName("coord")
    private Coordinator coordinator;

}
