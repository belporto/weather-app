package br.com.porto.isabel.weather.model.user;


import android.os.Parcel;
import android.os.Parcelable;

public class UserCity implements Parcelable {

    private String mName;
    private Double mLat;
    private Double mLon;
    private String mId;

    public UserCity(String name, Double lat, Double lon, String id) {
        mName = name;
        mLat = lat;
        mLon = lon;
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public Double getLat() {
        return mLat;
    }

    public Double getLon() {
        return mLon;
    }

    public String getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeValue(this.mLat);
        dest.writeValue(this.mLon);
        dest.writeString(this.mId);
    }

    protected UserCity(Parcel in) {
        this.mName = in.readString();
        this.mLat = (Double) in.readValue(Double.class.getClassLoader());
        this.mLon = (Double) in.readValue(Double.class.getClassLoader());
        this.mId = in.readString();
    }

    public static final Parcelable.Creator<UserCity> CREATOR = new Parcelable.Creator<UserCity>() {
        @Override
        public UserCity createFromParcel(Parcel source) {
            return new UserCity(source);
        }

        @Override
        public UserCity[] newArray(int size) {
            return new UserCity[size];
        }
    };
}
