package ru.geekbrains.cityinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CityInfoAdapter extends RecyclerView.Adapter<CityInfoAdapter.ViewHolder> {

    private List<CityInfo> dataSource;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityname;
        public ImageView picture;
        public TextView temperature;
        public TextView pressure;
        public TextView wind;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityname = itemView.findViewById(R.id.textView);
            picture = itemView.findViewById(R.id.imageView);
            temperature = itemView.findViewById(R.id.tempTextView);
            pressure = itemView.findViewById(R.id.presTextView);
            wind = itemView.findViewById(R.id.windTextView);
        }
    }

    public CityInfoAdapter (List<CityInfo> dataSource){
        this.dataSource=dataSource;
    }

    @NonNull
    @Override
    public CityInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_coatofarm, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    CityInfo cityInfo = dataSource.get(i);
    viewHolder.pressure.setText(cityInfo.getPressure());
    viewHolder.temperature.setText(cityInfo.getTemperature());
    viewHolder.picture.setImageResource(cityInfo.getImageIndex());
    viewHolder.cityname.setText(cityInfo.getCityName());
    viewHolder.wind.setText(cityInfo.getWind());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
