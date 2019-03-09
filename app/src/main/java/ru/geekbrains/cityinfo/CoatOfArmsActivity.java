package ru.geekbrains.cityinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

// Эта Activity для портретной ориентации
public class CoatOfArmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то Activity надо закрыть
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // Если Activity запускается первый раз (с каждым новым гербом первый раз)
            // то перенаправим параметр фрагменту
            CoatOfArmsFragment details = new CoatOfArmsFragment();
            details.setArguments(getIntent().getExtras());

            // Добавим фрагмент в Activity
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();
        }
    }
}
