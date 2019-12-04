package com.startandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.startandroid.data.NightMode;
import com.startandroid.module.Dialogs;
import com.startandroid.module.Offline;
import com.startandroid.utils.SignatureUtils;
import com.startandroid.utils.Utils;

import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;

import ru.svolf.melissa.sheet.SweetViewDialog;

import static android.app.Activity.RESULT_OK;
import static com.startandroid.data.Constants.IS_PREMIUM;
import static com.startandroid.data.Constants.OFFLINE;
import static com.startandroid.data.Constants.RESOURCES;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private boolean isPremium;
    private SwitchPreference offline;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        isPremium = requireActivity().getIntent().getBooleanExtra(IS_PREMIUM, false);
        offline = findPreference(OFFLINE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        switch (key) {
            case "offline":
                if (preferences.getBoolean(key, true) && isPremium && SignatureUtils.verifySignatureSHA(App.getContext()) || BuildConfig.DEBUG) {
                    if (Utils.isNetworkAvailable()) {
                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setTitle(getString(R.string.downloading));
                        new Offline(getActivity()).execute();
                    } else {
                        Dialogs.noConnectionError(getContext());
                    }
                } else if (isPremium) {
                    AsyncTask.execute(() -> {
                        try {
                            File resourcesDir = new File(requireActivity().getFilesDir(), RESOURCES);
                            FileUtils.deleteDirectory(resourcesDir);
                        } catch (IOException ignored) {
                        }
                    });
                } else if (preferences.getBoolean(key, false)) {
                    offline.performClick();
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.no_connection_error, null);
                    final SweetViewDialog dialog = new SweetViewDialog(getContext());
                    dialog.setTitle(R.string.error);
                    dialog.setView(v);
                    dialog.setPositive(android.R.string.ok, null);
                    dialog.show();
                }
                break;
            case "fullscreen_mode":
                restartPerfect(requireActivity().getIntent());
                break;
            case "night_mode":
                NightMode.setMode(NightMode.getCurrentMode());
                restartPerfect(requireActivity().getIntent());
                break;
            case "grid_mode":
                requireActivity().setResult(RESULT_OK);
                break;
        }
    }

    private void restartPerfect(Intent intent){
        requireActivity().finish();
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }
}
