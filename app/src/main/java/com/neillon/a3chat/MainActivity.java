package com.neillon.a3chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.neillon.a3chat.Firebase.MyDatabase;
import com.neillon.a3chat.Model.User;

import java.util.List;

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
        String usrkey = sharedPreferences.getString("usrkey", "");

        if (!nickname.equals("")){

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);

        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nickname = txtNickname.getText().toString();


                if (nickname.equals("")) {

                    Toast.makeText(MainActivity.this, "Informe um nickname", Toast.LENGTH_SHORT).show();
                    return;

                }else{

                   final DatabaseReference mdb = MyDatabase.getDatabase();
                   Query query = mdb.child("users").equalTo(nickname).orderByChild("name");
                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           if(dataSnapshot.getValue()!=null){
                               Toast.makeText(MainActivity.this, "Usuário existente!", Toast.LENGTH_SHORT).show();
                               Log.i("FIREBASE3", dataSnapshot.getValue().toString());

                           }else{

                               mdb.child("users").push().child("name").setValue(nickname, new DatabaseReference.CompletionListener() {
                                   @Override
                                   public void onComplete(@Nullable DatabaseError databaseError,
                                                          @NonNull DatabaseReference databaseReference) {

                                       String usrkey = databaseReference.push().getKey();

                                       if (keepConectedSwitcher.isChecked()) {
                                           SharedPreferences.Editor editor = sharedPreferences.edit();
                                           editor.putString(getString(R.string.nickname_key), nickname);
                                           editor.putString(getString(R.string.usr_key), usrkey);
                                           editor.apply();

                                           Intent i = new Intent(MainActivity.this, HomeActivity.class);

                                           Log.i("FIREBASE1", nickname);
                                           Log.i("FIREBASE2", usrkey);
                                            finish();
                                           startActivity(i);

                                       }else{

                                           Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                           startActivity(i);

                                       }
                                   }
                               });
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }

 /*                if (keepConectedSwitcher.isChecked()) {
                    sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.nickname_key), nickname);
                    editor.apply();
                }

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("nickname", nickname);
                startActivity(i);

                final DatabaseReference mdb = MyDatabase.getDatabase();
                Query query = mdb.child("users").equalTo(nickname).orderByChild("name");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()==null){
                            mdb.child("users").push().setValue("", new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(getString(R.string.nickname_key), nickname);
                                    editor.putString(getString(R.string.preferences_key), mdb.getKey());
                                }
                            });
                        }else{
                            Log.i("FIREBASE", dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
                //Log.i("FIREBASE", query.);
                //User user = new User("GRAUBERSSON");
                //mdb.child("users").push().setValue(user);

            }
        });
    }
}
