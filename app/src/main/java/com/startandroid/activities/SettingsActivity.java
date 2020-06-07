package com.startandroid.activities;

import android.os.Bundle;

import com.startandroid.R;
import com.startandroid.fragments.SettingsFragment;
import com.startandroid.model.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, new SettingsFragment())
                .commit();
    }
}