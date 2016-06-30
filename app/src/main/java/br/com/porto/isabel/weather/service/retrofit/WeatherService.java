package br.com.porto.isabel.weather.service.retrofit;


import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather?units=metric")
    Call<Current> getCurrent(@Query("APPID") String key, @Query("lat") double lat, @Query("lon") double lon);

    @GET("forecast/daily?units=metric")
    Call<Forecast> getDaily(@Query("APPID") String key,  @Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int numOfDays);
}
