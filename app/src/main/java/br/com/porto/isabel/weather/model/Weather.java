package br.com.porto.isabel.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Isabel Porto on 22/06/2016.
 */
public class Weather {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("main")
    private String mMain;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("icon")
    private String mIcon;

    public String getDescription() {
        return mDescription;
    }

    public String getCode(){
        return mIcon;
    }
}
