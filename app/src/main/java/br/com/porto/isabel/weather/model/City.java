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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Integer getPopulation() {
        return population;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
