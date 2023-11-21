package com.startandroid.activities

import android.os.Bundle
import com.startandroid.R
import com.startandroid.fragments.SettingsFragment

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_container, SettingsFragment())
            .commit()
    }
}