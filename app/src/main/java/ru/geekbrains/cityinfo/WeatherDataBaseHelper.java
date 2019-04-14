package ru.geekbrains.cityinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class WeatherDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weather.db"; // Название БД
    public static final int DATABASE_VERSION = 1; // Версия базы данных
    static final String TABLE_WEATHER = "citytemperature"; // Название таблицы в БД
    // Названия столбцов
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_CITYTEMPERATURE = "temperature";

    public WeatherDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_WEATHER + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT," + COLUMN_CITYTEMPERATURE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
