package br.com.porto.isabel.weather.model.openweather;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

    @SerializedName("temp")
    private Double mTemperature;

    @SerializedName("humidity")
    private Double mHumidity;

    @SerializedName("pressure")
    private Double mPressure;

    @SerializedName("temp_min")
    private Double mTempMin;

    @SerializedName("temp_max")
    private Double mTempMax;


    public Double getTemperature() {
        return mTemperature;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mTemperature);
        dest.writeValue(mHumidity);
        dest.writeValue(mPressure);
        dest.writeValue(mTempMin);
        dest.writeValue(mTempMax);
    }

    public Main() {
    }

    protected Main(Parcel in) {
        mTemperature = (Double) in.readValue(Double.class.getClassLoader());
        mHumidity = (Double) in.readValue(Double.class.getClassLoader());
        mPressure = (Double) in.readValue(Double.class.getClassLoader());
        mTempMin = (Double) in.readValue(Double.class.getClassLoader());
        mTempMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel source) {
            return new Main(source);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };
}
