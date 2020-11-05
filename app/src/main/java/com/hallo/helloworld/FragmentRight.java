package com.hallo.helloworld;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.HelloWorld.R;

public class FragmentRight extends Fragment {
    private static final String TAG = FragmentRight.class.getSimpleName();
    Button btnNotifTrigger;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(TAG, "onCreateView: FragmentBottom");
        return inflater.inflate(R.layout.fragmentright, container, false);
    }
}