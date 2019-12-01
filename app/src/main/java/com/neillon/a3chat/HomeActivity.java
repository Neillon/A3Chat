package com.neillon.a3chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.neillon.a3chat.adapter.TabAdapter;
import com.neillon.a3chat.fragment.ChatFragment;
import com.neillon.a3chat.fragment.PreferencesFragment;

public class HomeActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViewComponents();
        checkSavedPreferences();

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new ChatFragment(), "Chat");
        adapter.addFragment(new PreferencesFragment(), "Preferences");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void checkSavedPreferences() {
        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString(getString(R.string.nickname_key), "");

        if (nickname.equals("")) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            Toast.makeText(this, "Falha ao abrir aplicação", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(intent);
        }
    }

    private void initializeViewComponents() {
        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.fragment_tabs);
    }
}
