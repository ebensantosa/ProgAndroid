package com.hallo.helloworld;
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

public class LoginActivity extends AppCompatActivity {

    EditText TxUsername, TxPassword;
    Button BtnLogin,button;
    DatabaseHelper dbHelper;

    SharedPrefManager sharedPrefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TxUsername = (EditText)findViewById(R.id.txtUsername);
        TxPassword = (EditText)findViewById(R.id.txtPassword);
        BtnLogin = (Button)findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);
        sharedPrefManager = new SharedPrefManager(this);


        TextView tvCreateAccount = (TextView)findViewById(R.id.buatakun);
        tvCreateAccount.setText(fromHtml("Belum ada akun ? " +
                "</font><font color='#FFFFFF'>Buat Akun</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });


        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = TxUsername.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();

                Boolean res = dbHelper.checkUser(username,password);
                if(res == true){

                    Toast.makeText(LoginActivity.this, "Selamat Datang di Aplikasiku", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Silahkan cek E-mail dan Password anda", Toast.LENGTH_SHORT).show();
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
    public void signup(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
