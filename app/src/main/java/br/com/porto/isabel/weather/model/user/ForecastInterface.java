package br.com.porto.isabel.weather.model.user;

import android.os.Parcelable;

import java.util.List;

public interface ForecastInterface extends Parcelable {
    List<DailyInterface> getDailyList();
}
