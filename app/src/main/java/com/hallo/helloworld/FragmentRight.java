package com.hallo.helloworld;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hallo.HelloWorld.R;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentRight extends Fragment {
    private static final String TAG = FragmentRight.class.getSimpleName();

    private Button btnTambah;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;
    private FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();
    ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentRight");
        View view = inflater.inflate(R.layout.fragmentright, container, false);
        getDataMahasiswa();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.mhs_recycler_view);
        recyclerView.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(rvLayoutManager);
        btnTambah = (Button) getActivity().findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MahasiswaActivity.class);
                intent.putExtra("STATE", "Tambah");
                startActivity(intent);
            }
        });
    }

    private void getDataMahasiswa() {
        firebaseFirestoreDb.collection("mahasiswa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("DataMahasiswa", document.getId() + " => " + document.getData());
                        String nim = (String) document.getData().get("nim");
                        String nama = (String) document.getData().get("nama");
                        String phoneNo = (String) document.getData().get("phoneNo");
                        Mahasiswa mhs = new Mahasiswa(nim, nama, phoneNo);
                        mahasiswaList.add(mhs);
                        Log.d("DataMahasiswa", String.valueOf(mahasiswaList.size()));
                    }
                    rvAdapter = new MahasiswaAdapter(mahasiswaList);
                    recyclerView.setAdapter(rvAdapter);
                } else {
                    Log.w("DataMahasiswa", "Error getting documents.", task.getException());
                }
            }
        });
    }
}