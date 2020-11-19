package com.hallo.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hallo.HelloWorld.R;
import com.hallo.helloworld.HomeActivity;
import com.hallo.helloworld.Mahasiswa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MahasiswaActivity extends AppCompatActivity {
    private EditText txtNimMhs;
    private EditText txtNamaMhs;
    private EditText noPhoneMhs;
    private Button btnSimpan;
    private Button btnHapus;
    private Button btnKembali;

    private FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        txtNimMhs = findViewById(R.id.noMhs);
        txtNamaMhs = findViewById(R.id.namaMhs);
        noPhoneMhs = findViewById(R.id.phoneMhs);
        btnSimpan = findViewById(R.id.simpanButton);
        btnHapus = findViewById(R.id.hapusButton);
        btnKembali = findViewById(R.id.backButton);

        String getState = getIntent().getStringExtra("STATE");

        btnSimpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!txtNimMhs.getText().toString().isEmpty() && !txtNamaMhs.getText().toString().isEmpty()){
                    tambahMahasiswa();
                } else{
                    Toast.makeText(getApplicationContext(), "NIM dan Nama Mahasiswa tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataMahasiswa();
            }

        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembali();
            }
        });
        if(getState.equals("Edit")){
            getDataMahasiswa();
        }
    }


    private void tambahMahasiswa() {
        Mahasiswa mhs = new Mahasiswa(txtNimMhs.getText().toString(), txtNamaMhs.getText().toString(), noPhoneMhs.getText().toString());
        String docName = txtNamaMhs.getText().toString()+txtNimMhs.getText().toString();
        firebaseFirestoreDb.collection("DaftarMhs").document(docName).set(mhs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Mahasiswa berhasil didaftarkan", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "ERROR" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.toString());
            }
        });
    }

    private void getDataMahasiswa() {
        String doc = getIntent().getStringExtra("DOC");
        DocumentReference docRef = firebaseFirestoreDb.collection("DaftarMhs").document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nim = (String) document.getData().get("nim");
                        String nama = (String) document.getData().get("nama");
                        String phoneNo = (String) document.getData().get("phoneNo");
                        Mahasiswa mhs = document.toObject(Mahasiswa.class);
                        txtNimMhs.setText(mhs.getNim());
                        txtNamaMhs.setText(mhs.getNama());
                        noPhoneMhs.setText(mhs.getPhone());
                    } else {
                        Toast.makeText(getApplicationContext(), "Document tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Document error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteDataMahasiswa() {
        String doc = getIntent().getStringExtra("DOC");
        firebaseFirestoreDb.collection("DaftarMhs").document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        txtNimMhs.setText("");
                        txtNamaMhs.setText("");
                        noPhoneMhs.setText("");
                        Toast.makeText(getApplicationContext(), "Mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error deleting document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void kembali(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}