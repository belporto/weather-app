package br.com.porto.isabel.weather.model.openweather;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Coordinator implements Parcelable {

    @SerializedName("lat")
    private Double mLat;

    @SerializedName("lon")
    private Double mLong;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mLat);
        dest.writeValue(mLong);
    }

    public Coordinator() {
    }

    protected Coordinator(Parcel in) {
        mLat = (Double) in.readValue(Double.class.getClassLoader());
        mLong = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Coordinator> CREATOR = new Parcelable.Creator<Coordinator>() {
        @Override
        public Coordinator createFromParcel(Parcel source) {
            return new Coordinator(source);
        }

        @Override
        public Coordinator[] newArray(int size) {
            return new Coordinator[size];
        }
    };
}
