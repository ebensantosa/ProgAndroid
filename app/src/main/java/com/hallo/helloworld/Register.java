package com.hallo.helloworld;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hallo.HelloWorld.R;

public class Register extends AppCompatActivity {

    EditText TxUsername, TxPassword, TxConPassword;
    Button BtnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        TxUsername = (EditText)findViewById(R.id.txUsernameReg);
        TxPassword = (EditText)findViewById(R.id.txPasswordReg);
        TxConPassword = (EditText)findViewById(R.id.txConPassword);
        BtnRegister = (Button)findViewById(R.id.btnRegister);

        TextView tvRegister = (TextView)findViewById(R.id.tvRegister);

        tvRegister.setText(fromHtml("Anda sudah memiliki akun" +
                "</font><font color='#FFFFFF'> Kembali ke Login</font>"));

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LoginActivity.class));
            }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = TxUsername.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();
                String conPassword = TxConPassword.getText().toString().trim();

                ContentValues values = new ContentValues();


                if (!password.equals(conPassword)){
                    Toast.makeText(Register.this, "Password Belum Sama Cek kembali !", Toast.LENGTH_SHORT).show();
                }else if (password.equals("") || username.equals("")){
                    Toast.makeText(Register.this, "Silahkan Isi USername dan Password !", Toast.LENGTH_SHORT).show();
                }else {
                    values.put(DatabaseHelper.row_username, username);
                    values.put(DatabaseHelper.row_password, password);
                    dbHelper.insertData(values);

                    Toast.makeText(Register.this, "Akun Anda Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}