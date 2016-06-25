package br.com.porto.isabel.weather.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast implements Parcelable {
    @SerializedName("city")
    private City mCity;

    @SerializedName("list")
    private List<Daily> mDailyList;

    public List<Daily> getDailyList() {
        return mDailyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mCity, flags);
        dest.writeTypedList(mDailyList);
    }

    public Forecast() {
    }

    protected Forecast(Parcel in) {
        mCity = in.readParcelable(City.class.getClassLoader());
        mDailyList = in.createTypedArrayList(Daily.CREATOR);
    }

    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel source) {
            return new Forecast(source);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
}
