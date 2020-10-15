package com.hallo.helloworld;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class FragmentButtom extends Fragment {
    private static final String TAG = FragmentButtom.class.getSimpleName();
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentButtom");
        return inflater.inflate(R.layout.fragment_bottom, container, false);

    }
}
