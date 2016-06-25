package br.com.porto.isabel.weather.model;


import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("country")
    private String mCountry;

    @SerializedName("coord")
    private Coordinator mCoordinator;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }
}
