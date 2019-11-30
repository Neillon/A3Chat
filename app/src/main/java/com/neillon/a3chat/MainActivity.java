package com.neillon.a3chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.neillon.a3chat.configuration.FirestoreConfiguration;
import com.neillon.a3chat.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        initializeViewWidgets();
        verifyUserAlreadyLogged();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname = txtNickname.getText().toString();

                if (nickname.equals("")) {
                    Toast.makeText(MainActivity.this, "Informe um nickname", Toast.LENGTH_SHORT).show();
                    return;
                }

                addUserFirestore(nickname);
            }
        });

    }

    private void addUserFirestore(final String nickname) {
        final CollectionReference collection = FirestoreConfiguration.getCollection("users");

        final Map<String, Object> user = new HashMap<>();
        user.put("nickname", nickname);

        getUsers(nickname)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            int docs = task.getResult().getDocuments().size();
                            if (docs == 0) {
                                collection.document().set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                savePreferences(nickname);
                                                goToHome(nickname);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Login error", "Error writing document", e);
                                                Toast.makeText(MainActivity.this, "Erro ao realizar login", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                savePreferences(nickname);
                                goToHome(nickname);
                            }

                        }
                    }
                });
    }

    private Task<QuerySnapshot> getUsers(String nickname) {
        CollectionReference ref = FirestoreConfiguration.getCollection("users");
        Query query = ref
            .whereEqualTo("nickname", nickname);

        final List<User> users = new ArrayList<>();
        final boolean validation = false;

        return query.get();
    }

    private void verifyUserAlreadyLogged() {
        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString(getString(R.string.nickname_key), "");

        if (!nickname.equals("")){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            i.putExtra("nickname", nickname);
            finish();
            startActivity(i);
        }
    }

    private void savePreferences(String nickname) {
        if (keepConectedSwitcher.isChecked()) {
            sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.nickname_key), nickname);
            editor.apply();
        }
    }

    private void goToHome(String nickname) {
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        i.putExtra("nickname", nickname);
        finish();
        startActivity(i);
    }

    private void initializeViewWidgets() {
        txtNickname = findViewById(R.id.txt_nickname);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        keepConectedSwitcher = findViewById(R.id.switch_conected);
    }

}
