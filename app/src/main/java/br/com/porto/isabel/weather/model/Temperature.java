package br.com.porto.isabel.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Temperature implements Parcelable {

    @SerializedName("day")
    private Double mDay;

    @SerializedName("min")
    private Double mMin;

    @SerializedName("max")
    private Double mMax;

    @SerializedName("night")
    private Double mNight;

    @SerializedName("eve")
    private Double mEve;

    @SerializedName("morn")
    private Double mMorning;


    public Double getMinTemperature() {
        return mMin;
    }

    public Double getMaxTemperature() {
        return mMax;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mDay);
        dest.writeValue(this.mMin);
        dest.writeValue(this.mMax);
        dest.writeValue(this.mNight);
        dest.writeValue(this.mEve);
        dest.writeValue(this.mMorning);
    }

    public Temperature() {
    }

    protected Temperature(Parcel in) {
        this.mDay = (Double) in.readValue(Double.class.getClassLoader());
        this.mMin = (Double) in.readValue(Double.class.getClassLoader());
        this.mMax = (Double) in.readValue(Double.class.getClassLoader());
        this.mNight = (Double) in.readValue(Double.class.getClassLoader());
        this.mEve = (Double) in.readValue(Double.class.getClassLoader());
        this.mMorning = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel source) {
            return new Temperature(source);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };
}