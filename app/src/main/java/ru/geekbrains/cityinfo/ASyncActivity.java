package ru.geekbrains.cityinfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ASyncActivity extends AppCompatActivity {


    TextView status;
    TextView calc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        status = (TextView)findViewById(R.id.status);
        calc = (TextView)findViewById(R.id.calc);
        SyncCalc sc = new SyncCalc();
        sc.execute();
    }


    class SyncCalc extends AsyncTask<Void,Integer,Double>{


        @Override
        protected Double doInBackground(Void... voids) {
          Double result = calculate();
           return result;
        }

        @Override
        protected void onPreExecute() {
            status.setText("Расчет в процессе");
        }

        private Double calculate() {
            Integer j;
            double r = 1;
            for (j = 0; j < 100000; j++) {
                r = j*0.01+r/0.01;
                publishProgress(j);
            }
            return r;

        }

        @Override
        protected void onPostExecute(Double d) {
            status.setText("Расчет завершен");
            calc.setText(d.toString());
            super.onPostExecute(d);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            status.setText("Сейчас считается "+ values[0]);
        }

    }
}
