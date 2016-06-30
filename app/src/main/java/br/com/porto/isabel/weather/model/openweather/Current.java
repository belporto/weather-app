package br.com.porto.isabel.weather.model.openweather;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.model.app.CurrentInterface;

public class Current implements CurrentInterface {

    @SerializedName("coord")
    private Coordinator mCoordinator;

    @SerializedName("weather")
    private List<Weather> mWeatherList;

    @SerializedName("name")
    private String mCityName;

    @SerializedName("id")
    private String mCityId;

    @SerializedName("dt")
    private String mDate;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("wind")
    private Wind mWind;

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    @Override
    public String getCityName() {
        return mCityName;
    }

    @Override
    public String getWeatherDescription() {
        Weather weather = mWeatherList.get(0);
        return weather.getDescription();
    }

    @Override
    public String getWeatherCode() {
        Weather weather = mWeatherList.get(0);
        return weather.getCode();
    }

    @Override
    public int getCurrentTemperature() {
        return getValue(mMain.getTemperature());
    }


    @Override
    public int getPressure() {
        return getValue(mMain.getPressure());
    }

    @Override
    public int getHumidity() {
        return getValue(mMain.getHumidity());
    }

    @Override
    public int getWindSpeed() {
        int speed = getValue(mWind.getSpeed());
        Double speedKMH = speed * 3.6;
        return speedKMH.intValue();
    }

    @Override
    public int getWindDegree() {
        return getValue(mWind.getDegree());
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
        dest.writeParcelable(mCoordinator, flags);
        dest.writeList(mWeatherList);
        dest.writeString(mCityName);
        dest.writeString(mCityId);
        dest.writeString(mDate);
        dest.writeParcelable(mMain, flags);
        dest.writeParcelable(mWind, flags);
    }

    public Current() {
    }

    protected Current(Parcel in) {
        mCoordinator = in.readParcelable(Coordinator.class.getClassLoader());
        mWeatherList = new ArrayList<Weather>();
        in.readList(mWeatherList, Weather.class.getClassLoader());
        mCityName = in.readString();
        mCityId = in.readString();
        mDate = in.readString();
        mMain = in.readParcelable(Main.class.getClassLoader());
        mWind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Parcelable.Creator<Current> CREATOR = new Parcelable.Creator<Current>() {
        @Override
        public Current createFromParcel(Parcel source) {
            return new Current(source);
        }

        @Override
        public Current[] newArray(int size) {
            return new Current[size];
        }
    };


}
