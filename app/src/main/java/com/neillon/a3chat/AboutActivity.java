package com.neillon.a3chat;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private TextView txtA3, txtAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        this.txtA3 = findViewById(R.id.txtA3);
        this.txtAbout = findViewById(R.id.txtAbout);

        //TextView secondTextView = new TextView(this);
        Shader textShader = new LinearGradient(0, 0, 0, 20,
                new int[]{ getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimaryDark)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        this.txtA3.getPaint().setShader(textShader);
        this.txtAbout.getPaint().setShader(textShader);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
