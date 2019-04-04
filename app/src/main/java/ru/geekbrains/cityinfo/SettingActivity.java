package ru.geekbrains.cityinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private final String APP_PREFERENCES = "CitiesSettings";
    private final String APP_PREFERENCES_SHOWTEMP = "ShowTemperature";
    private final String APP_PREFERENCES_SHOWWIND = "ShowWind";
    SharedPreferences mSettings;
    private final Activity that = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializate();
    }

    private void initializate() {
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final CheckBox checkTemp = (CheckBox)findViewById(R.id.showtemp);
        final CheckBox checkWind = (CheckBox)findViewById(R.id.showwind);
        Button saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(APP_PREFERENCES_SHOWTEMP,checkTemp.isChecked());
                editor.putBoolean(APP_PREFERENCES_SHOWWIND,checkWind.isChecked());
                editor.commit();
                Toast.makeText(getApplicationContext(),"Настройки сохранены",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(that ,MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
