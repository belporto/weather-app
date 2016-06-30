package br.com.porto.isabel.weather.model.openweather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Weather implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mId);
        dest.writeString(mMain);
        dest.writeString(mDescription);
        dest.writeString(mIcon);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        mId = (Integer) in.readValue(Integer.class.getClassLoader());
        mMain = in.readString();
        mDescription = in.readString();
        mIcon = in.readString();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
