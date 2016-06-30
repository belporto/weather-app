package br.com.porto.isabel.weather.model.openweather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import br.com.porto.isabel.weather.model.app.DailyInterface;

public class Daily implements DailyInterface {

    @SerializedName("dt")
    private Long mUnixDate;

    @SerializedName("temp")
    private Temperature mTemperature;

    @SerializedName("pressure")
    private Double mPressure;

    @SerializedName("humidity")
    private Double mHumidity;

    @SerializedName("weather")
    private List<Weather> mWeatherList;

    @SerializedName("speed")
    private Double mWindSpeed;

    @SerializedName("deg")
    private Double mWindDirection;

    @SerializedName("clouds")
    private Double mClouds;

    @Override
    public int getMinTemperature() {
        return getValue(mTemperature.getMinTemperature());
    }

    @Override
    public int getMaxTemperature() {
        return getValue(mTemperature.getMaxTemperature());
    }

    @Override
    public Date getDate() {
        return new Date(mUnixDate * 1000L);
    }

    @Override
    public int getHumidity() {
        return getValue(mHumidity);
    }

    @Override
    public String getWeatherCode() {
        return mWeatherList.get(0).getCode();
    }

    @Override
    public int getWindSpeed() {
        int speed = getValue(mWindSpeed);
        Double speedKMH = speed * 3.6;
        return speedKMH.intValue();
    }

    @Override
    public int getWindDegree() {
        return getValue(mWindDirection);
    }

    @Override
    public int getPressure() {
        return getValue(mPressure);
    }

    @Override
    public String getWeatherDescription() {
        return mWeatherList.get(0).getDescription();
    }

    private int getValue(Double value) {
        if (value == null) {
            value = 0.0;
        }
        return value.intValue();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mUnixDate != null ? this.mUnixDate : -1);
        dest.writeParcelable(this.mTemperature, flags);
        dest.writeValue(this.mPressure);
        dest.writeValue(this.mHumidity);
        dest.writeTypedList(this.mWeatherList);
        dest.writeValue(this.mWindSpeed);
        dest.writeValue(this.mWindDirection);
        dest.writeValue(this.mClouds);
    }

    public Daily() {
    }

    protected Daily(Parcel in) {
        this.mUnixDate = in.readLong();
        this.mTemperature = in.readParcelable(Temperature.class.getClassLoader());
        this.mPressure = (Double) in.readValue(Double.class.getClassLoader());
        this.mHumidity = (Double) in.readValue(Double.class.getClassLoader());
        this.mWeatherList = in.createTypedArrayList(Weather.CREATOR);
        this.mWindSpeed = (Double) in.readValue(Double.class.getClassLoader());
        this.mWindDirection = (Double) in.readValue(Double.class.getClassLoader());
        this.mClouds = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Daily> CREATOR = new Parcelable.Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };


}
