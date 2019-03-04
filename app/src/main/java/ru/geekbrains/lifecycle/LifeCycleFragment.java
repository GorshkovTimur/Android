package ru.geekbrains.lifecycle;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LifeCycleFragment extends Fragment {
    private static final String TAG = "MyFrag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"onCreatView");
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onPause() {
        Log.i(TAG,"onPause");
        super.onPause();

    }
}
