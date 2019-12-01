package com.neillon.a3chat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neillon.a3chat.adapter.TabAdapter;
import com.neillon.a3chat.fragment.ChatFragment;
import com.neillon.a3chat.fragment.PreferencesFragment;

public class HomeActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;

    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViewComponents();
        checkSavedPreferences();

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new ChatFragment(nickname), "Chat");
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
        } else {
            this.nickname = nickname;
        }
    }

    private void initializeViewComponents() {
        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.fragment_tabs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.menuSobre:
                i = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.menuSair:
                eraseSharedPreferences();
                i = new Intent(HomeActivity.this, MainActivity.class);
                getWindow().setExitTransition(new Explode());
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                finish();

            return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void eraseSharedPreferences() {
        sharedPreferences = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
