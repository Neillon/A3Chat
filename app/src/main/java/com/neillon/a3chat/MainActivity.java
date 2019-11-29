package com.neillon.a3chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText txtNickname;
    private FloatingActionButton btnLogin;
    private MaterialButton btnRegister;
    private SwitchMaterial keepConectedSwitcher;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getSupportActionBar().hide();

        txtNickname = findViewById(R.id.txt_nickname);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        keepConectedSwitcher = findViewById(R.id.switch_conected);

        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString(getString(R.string.nickname_key), "");

        if (!nickname.equals("")){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            i.putExtra("nickname", nickname);
            startActivity(i);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname = txtNickname.getText().toString();

                if (nickname.equals("")) {
                    Toast.makeText(MainActivity.this, "Informe um nickname", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (keepConectedSwitcher.isChecked()) {
                    sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.nickname_key), nickname);
                    editor.apply();
                }

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("nickname", nickname);
                startActivity(i);
            }
        });
    }
}
