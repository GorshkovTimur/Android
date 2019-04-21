package ru.geekbrains.cityinfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import ru.geekbrains.cityinfo.interfaces.OpenWeatherBy;
import ru.geekbrains.cityinfo.model.WeatherRequest;

public class ActivityOpenWeather extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private LocationManager locationManager;
    private String provider;
    private WeatherDataSource wSource;
    private OpenWeather ow;
    private OpenWeatherBy owb;
    private TextView temp;
    private Button showTemp;
    private Button showTempBy;
    private EditText editCity;
    private final String KEY = "29fb8d6056fe0f462c0731feda71d342";
    private final Float K2C = 273F;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);
        initRetrofit();
        initGUI();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        }

        else{
            requestLocationPermissions();
        }


        wSource = new WeatherDataSource(getApplicationContext());
        wSource.open();
        showTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRetrofit(editCity.getText().toString(),KEY);
            }
        });

        showTempBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
                StringBuilder sb = new StringBuilder();
                sb.append(lat).append(" ").append(lon);
                Log.d("Pudel", String.valueOf(sb));
                requestRetrofitBy(lat,lon,KEY);
            }
        });



    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }

    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        provider = locationManager.getBestProvider(criteria,true);
        if (provider!=null){
            locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat = location.getAltitude();
                    lon = location.getLongitude();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

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


    }


    private void requestRetrofitBy(double lan, double lon, String key){
        final StringBuilder sb = new StringBuilder();
        owb.loadWeather(lan,lon,key)
                .enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                if (response.body()!=null)
                    sb.append("Температура ").append(Float.toString(response.body().getMain().getTemp()-K2C)).append("*C");
                temp.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<WeatherRequest> call, Throwable t) {
                temp.setText("Sorry");
            }
        });


    }

    private void initGUI() {
        temp =  findViewById(R.id.temperature);
        showTemp = findViewById(R.id.getTemp);
        showTempBy = findViewById(R.id.getTempBy);
        editCity = findViewById(R.id.city);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ow = retrofit.create(OpenWeather.class);
        owb = retrofit.create(OpenWeatherBy.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                requestLocation();
            }
        }
    }




}
