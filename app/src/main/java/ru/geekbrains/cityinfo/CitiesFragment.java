package ru.geekbrains.cityinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

// Фрагмент для выбора города из списка
public class CitiesFragment extends ListFragment {

    private static final String KEY = "CurrentCity";

    private boolean isExistCoatOfArms;  // Можно ли расположить рядом фрагмент с гербом
    private Parcel currentParcel;


    SharedPreferences mSettings;


    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Button button = (Button) view.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AllInfoActivity.class);
                        startActivity(intent);
                    }
                });

        Button sensButton = (Button)view.findViewById(R.id.button_sensors);
        sensButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SensorInfoActivity.class);
                startActivity(intent);
            }
        });

        Button settings_Button = (Button)view.findViewById(R.id.button_settings);
        settings_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });

        Button async = (Button)view.findViewById(R.id.async);
        async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ASyncActivity.class);
                startActivity(intent);
            }
        });

        Button cview = (Button)view.findViewById(R.id.custom_view);
        cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),ActivityCustomView.class);
                startActivity(in);
            }
        });

                return view;


    }

    // Активити создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Для того, чтобы показать список, надо задействовать адаптер.
        // Такая конструкция работает для списков - например, ListActivity.
        // Создаем из ресурсов список городов
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities, android.R.layout.simple_list_item_activated_1);

        setListAdapter(adapter);

        // Определяем, можно ли будет расположить рядом герб (в другом фрагменте)
        View detailsFrame = getActivity().findViewById(R.id.coat_of_arms);
        // getActivity - получить Activity, в которой расположен фрагмент
        isExistCoatOfArms = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // Если это не повторное создание, то восстановим текущую позицию
        if (savedInstanceState != null)
            currentParcel = (Parcel) savedInstanceState.getSerializable(KEY);
        else
            currentParcel = new Parcel(0, getResources().getTextArray(R.array.Cities)[0].toString());

        // Если можно нарисовать рядом герб, то сделаем это
        if (isExistCoatOfArms) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms(currentParcel);
        }

    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY, currentParcel);
    }

    // Обработка выбора позиции
    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        TextView cityNameView = (TextView) view;
        currentParcel =  new Parcel(position, cityNameView.getText().toString());
        showCoatOfArms(currentParcel);
    }

    // Показать герб; если возможно - то показать рядом со списком,
    // в противном случае открыть вторую Activity
    private void showCoatOfArms(Parcel parcel) {
        if (isExistCoatOfArms) {
            // Выделим текущий элемент списка
            getListView().setItemChecked( parcel.getImageIndex(), true);

            // Проверим, что фрагмент с гербом существует в Activity
            CoatOfArmsFragment detail = (CoatOfArmsFragment)
                    getFragmentManager().findFragmentById(R.id.coat_of_arms);
            // если есть необходимость, то выведем герб
            if (detail == null || detail.getParcel().getImageIndex() != parcel.getImageIndex()) {

                // Создаем новый фрагмент с текущей позицией (для вывода герба)
                detail = CoatOfArmsFragment.create(parcel);

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            // Если нельзя вывести герб рядом, откроем вторую Activity
            Intent intent = new Intent();
            intent.setClass(getActivity(), CoatOfArmsActivity.class);
            // и передадим в нее параметры
            intent.putExtra(CoatOfArmsFragment.PARCEL, parcel);
            startActivity(intent);
        }
    }
}
