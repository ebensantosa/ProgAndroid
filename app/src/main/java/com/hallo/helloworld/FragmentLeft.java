package com.hallo.helloworld;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HelloWorld.R;

public class FragmentLeft extends Fragment {
    private static final String TAG = FragmentLeft.class.getSimpleName();

    String[] film;
    String[] sinopsis;
    RecyclerView recyclerView;
    int[] image ={R.drawable.naruto,R.drawable.bleach,R.drawable.onepiece,R.drawable.jojo,R.drawable.doraemon
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenleft, container, false);
        film = getResources().getStringArray(R.array.kumpulanFilm);
        sinopsis = getResources().getStringArray(R.array.cerita);
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recycleViewAdapter = new RecyclerViewAdapter(requireContext(),film,sinopsis,image);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }
}