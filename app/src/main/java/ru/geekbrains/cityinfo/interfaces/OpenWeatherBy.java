package ru.geekbrains.cityinfo.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.cityinfo.model.WeatherRequest;

public interface OpenWeatherBy {

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather (@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String keyApi);

}



