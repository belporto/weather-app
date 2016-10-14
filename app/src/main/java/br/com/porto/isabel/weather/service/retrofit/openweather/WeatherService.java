package br.com.porto.isabel.weather.service.retrofit.openweather;


import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherService {
    @GET("weather?units=metric")
    Observable<Current> getCurrent(@Query("APPID") String key, @Query("lat") double lat, @Query("lon") double lon);

    @GET("forecast/daily?units=metric")
    Observable<Forecast> getDaily(@Query("APPID") String key,  @Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int numOfDays);
}
