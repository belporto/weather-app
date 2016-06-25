package br.com.porto.isabel.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Wind implements Parcelable {

    @SerializedName("speed")
    private Double mSpeed;

    @SerializedName("deg")
    private Double mDegree;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mSpeed);
        dest.writeValue(mDegree);
    }

    public Wind() {
    }

    protected Wind(Parcel in) {
        mSpeed = (Double) in.readValue(Double.class.getClassLoader());
        mDegree = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}
