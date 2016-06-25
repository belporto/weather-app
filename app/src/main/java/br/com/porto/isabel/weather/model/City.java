package br.com.porto.isabel.weather.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mId);
        dest.writeString(mName);
        dest.writeString(mCountry);
        dest.writeParcelable(mCoordinator, flags);
    }

    public City() {
    }

    protected City(Parcel in) {
        mId = (Integer) in.readValue(Integer.class.getClassLoader());
        mName = in.readString();
        mCountry = in.readString();
        mCoordinator = in.readParcelable(Coordinator.class.getClassLoader());
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
