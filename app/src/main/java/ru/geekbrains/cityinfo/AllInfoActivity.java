package ru.geekbrains.cityinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class AllInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_cities_info);
        RecyclerView rv = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);

        DataSourcebuilder sourcebuilder = new DataSourcebuilder(getResources());
        CityInfoAdapter adapter = new CityInfoAdapter(sourcebuilder.build());
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь вылетит Snackbar
                Snackbar.make(view, "Спасибо за внимание", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    }
