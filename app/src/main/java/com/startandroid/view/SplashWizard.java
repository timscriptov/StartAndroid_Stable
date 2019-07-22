package com.startandroid.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.startandroid.MainActivity;
import com.startandroid.R;

import static com.startandroid.data.Preferences.setFirstLaunch;
import static com.startandroid.data.Preferences.setFullscreenMode;
import static com.startandroid.data.Preferences.setLang;
import static com.startandroid.data.Preferences.setNightMode;

public class SplashWizard extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.splash_wizard, null);

        final RadioGroup langSelect = view.findViewById(R.id.langSelect);
        final Switch nightTheme = view.findViewById(R.id.nightTheme);
        final Switch fullscreenMode = view.findViewById(R.id.fullscreenMode);
        final Button done = view.findViewById(R.id.done);

        langSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup p1, int p2) {
                switch (langSelect.getCheckedRadioButtonId()) {
                    case R.id.eng:
                        setLang("en");
                        break;
                    case R.id.rus:
                        setLang("ru");
                        break;
                    case R.id.uk:
                        setLang("uk");
                }
            }
        });

        nightTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton p1, boolean p2) {
                setNightMode(p2);
            }
        });

        fullscreenMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton p1, boolean p2) {
                setFullscreenMode(p2);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                setFirstLaunch(false);
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
