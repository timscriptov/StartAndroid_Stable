package com.startandroid.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.startandroid.R;
import com.startandroid.data.NightMode;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    ListPreference font_size;
    ListPreference lang;

    @Override
    public void onSharedPreferenceChanged(SharedPreferences p1, String p2) {
        switch (p2) {
            case "fullscreen_mode":
                getActivity().recreate();
                break;
            case "night_mode":
                NightMode.setMode(NightMode.getCurrentMode());
                getActivity().recreate();
                break;
            case "grid_mode":
                getActivity().setResult(Activity.RESULT_OK);
                break;
            case "lang":
                lang.setSummary(lang.getEntry());
                break;
            case "font_size":
                font_size.setSummary(font_size.getValue() + "%");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        font_size = (ListPreference) findPreference("font_size");
        font_size.setSummary(font_size.getValue() + "%");
        lang = (ListPreference) findPreference("lang");
        lang.setSummary(lang.getEntry());
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}
