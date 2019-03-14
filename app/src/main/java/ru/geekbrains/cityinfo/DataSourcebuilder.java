package ru.geekbrains.cityinfo;

import java.util.ArrayList;
import java.util.List;
import android.content.res.Resources;
import android.content.res.TypedArray;


public class DataSourcebuilder {

    private List<CityInfo> dataSource;
    private Resources resources;


    public DataSourcebuilder(Resources resources) {
        dataSource = new ArrayList<CityInfo>(6);
        this.resources = resources;
    }

    public List<CityInfo> build(){
        String[] cities = resources.getStringArray(R.array.Cities);
        String[] temperature = resources.getStringArray(R.array.city_temperature);
        String[] pressure = resources.getStringArray(R.array.city_pressure);
        String[] wind = resources.getStringArray(R.array.city_wind);
        int[] pictures = getImageArray();
        for (int i=0 ; i < cities.length ; i++){
        dataSource.add(new CityInfo(pictures[i], cities[i], temperature[i], pressure[i], wind[i]));
        }
        return dataSource;
    }

    private int[] getImageArray() {
        TypedArray pictures = resources.obtainTypedArray(R.array.coatofarms_imgs);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i=0;i<length;i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }
}
