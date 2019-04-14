package ru.geekbrains.cityinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.cityinfo.interfaces.OpenWeather;
import ru.geekbrains.cityinfo.model.WeatherRequest;

public class ActivityOpenWeather extends AppCompatActivity {

    private WeatherDataSource wSource;
    private OpenWeather ow;
    private TextView temp;
    private Button showTemp;
    private EditText editCity;
    private final String KEY = "29fb8d6056fe0f462c0731feda71d342";
    private final Float K2C = 273F;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);
        initRetrofit();
        initGUI();
        wSource = new WeatherDataSource(getApplicationContext());
        wSource.open();
        showTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRetrofit(editCity.getText().toString(),KEY);
            }
        });



    }

    private void requestRetrofit(String s, String key) {
       final StringBuilder sb = new StringBuilder();
       final String city = s;
    ow.loadWeather(s,key)
    .enqueue(new Callback<WeatherRequest>() {
        @Override
        public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
        if (response.body()!=null)
            sb.append("Температура ").append(Float.toString(response.body().getMain().getTemp()-K2C)).append("*C");
            temp.setText(sb.toString());
            wSource.addWeather(city,Float.toString(response.body().getMain().getTemp()-K2C));
            wSource.close();
        }

        @Override
        public void onFailure(Call<WeatherRequest> call, Throwable t) {
            temp.setText("Sorry");

        }
    });
    ;

    }

    private void initGUI() {
        temp = (TextView) findViewById(R.id.temperature);
        showTemp = (Button)findViewById(R.id.getTemp);
        editCity = (EditText)findViewById(R.id.city);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ow = retrofit.create(OpenWeather.class);
    }





}
