package ru.geekbrains.socnet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// адаптер
public class SocnetAdapter extends RecyclerView.Adapter<SocnetAdapter.ViewHolder> {

    private final List<Soc> dataSource;                         // Наш источник данных
    private OnItemClickListener itemClickListener;  // Слушатель, будет устанавливаться извне
    boolean[] checked;

    // этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один элемент списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView description;
        private final ImageView picture;
        private final CheckBox like;

        public ViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.description);
            picture = view.findViewById(R.id.picture);
            like = view.findViewById(R.id.like);

            // обработчик нажатий в этом ViewHolder
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    // интерфейс для обработки нажатий (как в ListView)
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public SocnetAdapter(List<Soc> dataSource) {
        this.dataSource = dataSource;
        checked = new boolean[dataSource.size()];
    }

    // Создать новый элемент пользовательского интерфейса
    @NonNull
    @Override
    public SocnetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса через Inflater
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // Здесь можно установить параметры
        ViewHolder vh = new ViewHolder(view);

        // на каком-то этапе будет переиспользование карточки, и в лог эта строка не попадет
        // а строка onBindViewHolder попадет, это будет означать, что старая карточка
        // переоткрыта с новыми данными
        Log.d("SocnetAdapter", "onCreateViewHolder");
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Получить элемент из источника данных (БД, интернет…)
        Soc item = dataSource.get(position);

        // Вывести на экран, используя ViewHolder
        holder.description.setText(item.getDescription());
        holder.picture.setImageResource(item.getPicture());
        holder.like.setChecked(checked[position]);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position]=!checked[position];
            }
        });


        // отрабатывает при необходимости нарисовать карточку
        Log.d("SocnetAdapter", "onBindViewHolder");
    }

    public boolean[] getChecked(){
        return checked;}

    // Вернуть размер данных
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
