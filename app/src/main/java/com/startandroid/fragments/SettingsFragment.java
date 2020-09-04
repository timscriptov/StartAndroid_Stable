package com.startandroid.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.startandroid.BuildConfig;
import com.startandroid.R;
import com.startandroid.async.Offline;
import com.startandroid.data.Dialogs;
import com.startandroid.data.NightMode;
import com.startandroid.interfaces.OfflineListener;
import com.startandroid.utils.FileUtils;
import com.startandroid.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    OfflineListener mListener = new OfflineListener() {
        @Override
        public void onProcess() {

        }

        @Override
        public void onCompleted() {
            Toasty.success(getContext(), R.string.offline_activated).show();
        }

        @Override
        public void onFailed() {
            com.startandroid.utils.FileUtils.deleteOffline(getContext());
        }

        @Override
        public void onCanceled() {
            com.startandroid.utils.FileUtils.deleteOffline(getContext());
        }
    };
    private boolean isPremium;
    private SwitchPreference offline;
    private Preference downloadResources;
    private Preference clearCache;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        isPremium = requireActivity().getIntent().getBooleanExtra("isPremium", false);
        offline = findPreference("offline");

        downloadResources = findPreference("download_offline");
        downloadResources.setOnPreferenceClickListener(p1 -> {
            try {
                if (Utils.isNetworkAvailable()) {
                    if (isPremium || BuildConfig.DEBUG) {
                        new Offline(mListener, getActivity()).execute();
                    } else {
                        FileUtils.deleteOffline(getContext());
                        Dialogs.show(getActivity(), getString(R.string.only_prem));
                    }
                } else {
                    Dialogs.noConnectionError(getActivity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

        clearCache = findPreference("clear_cache");
        clearCache.setOnPreferenceClickListener(p1 -> {
            AsyncTask.execute(() -> {
                com.startandroid.utils.FileUtils.deleteOffline(getContext());
            });
            return true;
        });
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
    public void onSharedPreferenceChanged(SharedPreferences preferences, @NotNull String key) {
        switch (key) {
            case "offline":
                if (offline.isChecked()) {
                    File resourcesDir = new File(getContext().getFilesDir(), "resources");
                    if (resourcesDir.exists()) {
                        restartPerfect(requireActivity().getIntent());
                    } else {
                        offline.setChecked(false);
                        Toasty.success(getContext(), R.string.not_installed_offline).show();
                    }
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

    private void restartPerfect(Intent intent) {
        requireActivity().finish();
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }
}
