package com.hallo.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HelloWorld.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: dipanggil");
        Log.i(TAG, "onCreate: perubahan");

        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(txtUsername.getText().toString().equals("admin") && txtPassword.getText().toString().equals("admin")){
                   onClickberhasil();

                }else{
                    Toast.makeText(getApplicationContext(), "Username atau Password Anda tidak benar!", Toast.LENGTH_SHORT).show();
                }

            }
            private void onClickberhasil(){
                setContentView(R.layout.activity_home);
                startActivity(new Intent( getApplicationContext() , HomeActivity.class));
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                intent.putExtra("COBA_INTENT_EXTRA", "Percobaan");
                startActivity(intent);
            }

        });
    }

}