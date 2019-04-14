package ru.geekbrains.cityinfo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SensorInfoActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorTemp;
    private Sensor sensorHum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        final TextView tempText = (TextView)findViewById(R.id.temp_from_sens);
        final TextView humiText = (TextView)findViewById(R.id.hum_from_sens);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHum = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        SensorEventListener listenerTemp =  new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                showSensorInfo("Температура ", tempText,sensorTemp,event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorEventListener listenerHum = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                showSensorInfo("Влажность ", humiText,sensorHum,event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(listenerTemp,sensorTemp,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHum,sensorHum,SensorManager.SENSOR_DELAY_NORMAL);


    }

    private void showSensorInfo(String criteria, TextView text, Sensor sensor, SensorEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(criteria+": ").append(event.values[0]);
        text.setText(sb);
    }

}


