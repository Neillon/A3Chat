package com.neillon.a3chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.neillon.a3chat.Firebase.MyDatabase;

public class HomeActivity extends AppCompatActivity {

    private MaterialTextView txtNickname;
    private SharedPreferences sharedPreferences;
    private MaterialButton btnOut;
    private String usrkey, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnOut = findViewById(R.id.btnOut);
        txtNickname = findViewById(R.id.txt_nickname);
        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString(getString(R.string.nickname_key), "");
        usrkey = sharedPreferences.getString("usrkey", "");
        Log.i("FIREBASE4", nickname);
        Log.i("FIREBASE5", usrkey);
        if (!nickname.equals(""))
            txtNickname.setText(nickname);
        else
            txtNickname.setText("Realize login seu merda!");
    }

    public void out(View v){



        final DatabaseReference mdb = MyDatabase.getDatabase();
        Log.i("FIREBASE10", usrkey);
        Query query = mdb.child("users").orderByChild("name").equalTo(usrkey);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().removeValue();
                Log.i("DATASNAP", dataSnapshot.getRef().toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();

                finish();
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "NÃO FOI POSSIVEL EXCLUIR!" + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
