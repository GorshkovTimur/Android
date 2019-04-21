package ru.geekbrains.cityinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;

public class WeatherDataSource implements Closeable {

    private Cursor cursor ;

    private WeatherDataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public WeatherDataSource(Context context) {
        dbHelper = new WeatherDataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        // Создать читателя и открыть его
    }

    public void close() {
        dbHelper.close();
    }

    public void addWeather (String city, String temperature){
        ContentValues values = new ContentValues();
        values.put(WeatherDataBaseHelper.COLUMN_CITY, city);
        values.put(WeatherDataBaseHelper.COLUMN_CITYTEMPERATURE, temperature);
        database.insert(WeatherDataBaseHelper.TABLE_WEATHER, null, values);
    }

}
