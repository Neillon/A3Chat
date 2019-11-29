package com.neillon.a3chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.textview.MaterialTextView;

public class HomeActivity extends AppCompatActivity {

    private MaterialTextView txtNickname;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNickname = findViewById(R.id.txt_nickname);

        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString(getString(R.string.nickname_key), "");

        if (!nickname.equals(""))
            txtNickname.setText(nickname);
        else
            txtNickname.setText("Realize login seu merda!");
    }
}
