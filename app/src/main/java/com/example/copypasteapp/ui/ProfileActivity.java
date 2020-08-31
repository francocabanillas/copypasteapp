package com.example.copypasteapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.copypasteapp.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
    }
}