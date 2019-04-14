package ru.geekbrains.cityinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// Фрагмент для вывода герба
public class CoatOfArmsFragment extends Fragment {

    public static final String APP_PREFERENCES = "CitiesSettings";
    SharedPreferences mSettings;

    private final String APP_PREFERENCES_SHOWTEMP = "ShowTemperature";
    private final String APP_PREFERENCES_SHOWWIND = "ShowWind";

    public static final String PARCEL = "parcel";
    private boolean isShowTemp;
    private boolean isShowWind;

    // фабричный метод, создает фрагмент и передает параметр
    public static CoatOfArmsFragment create(Parcel parcel) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment();    // создание

        // передача параметра
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    // получить индекс из списка (фактически из параметра)
    public Parcel getParcel() {
        return (Parcel) getArguments().getSerializable(PARCEL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_coatofarm, container, false);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        isShowTemp = mSettings.getBoolean(APP_PREFERENCES_SHOWTEMP,true);
        isShowWind = mSettings.getBoolean(APP_PREFERENCES_SHOWWIND,true);


        // определить какой герб надо показать (и показать его)
        ImageView coatOfArms  = layout.findViewById(R.id.imageView);
        TextView cityNameView = layout.findViewById(R.id.textView);
        TextView cityTemperature = layout.findViewById(R.id.tempTextView);
        TextView cityPressure = layout.findViewById(R.id.presTextView);
        TextView cityWind = layout.findViewById(R.id.windTextView);

        // получить из ресурсов массив указателей на изображения гербов
        TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
        TypedArray temperature = getResources().obtainTypedArray(R.array.city_temperature);
        TypedArray pressure = getResources().obtainTypedArray(R.array.city_pressure);
        TypedArray wind = getResources().obtainTypedArray(R.array.city_wind);
        Parcel parcel = getParcel();

        // выбрать по индексу подходящий
        coatOfArms.setImageResource(images.getResourceId(parcel.getImageIndex(), -1));
                images.recycle();

        cityTemperature.setText(temperature.getText(parcel.getImageIndex()));
        cityPressure.setText(pressure.getText(parcel.getImageIndex()));
        cityWind.setText(wind.getText(parcel.getImageIndex()));
        cityNameView.setText(parcel.getCityName());

        if(!isShowTemp){
            cityTemperature.setVisibility(View.INVISIBLE);
        }
        if(!isShowWind){
            cityWind.setVisibility(View.INVISIBLE);
        }



        return layout;
    }
}
