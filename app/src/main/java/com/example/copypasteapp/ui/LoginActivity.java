package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.copypasteapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void ingresar(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void registrar(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void restablecer(View view){
        startActivity(new Intent(this, ResetpasswordActivity.class));
    }
}