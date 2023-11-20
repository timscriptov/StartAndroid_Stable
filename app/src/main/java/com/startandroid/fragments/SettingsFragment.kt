package com.startandroid.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.startandroid.R
import com.startandroid.activities.MainActivity
import com.startandroid.data.Dialogs.noConnectionError
import com.startandroid.data.Preferences
import com.startandroid.entity.Offline
import com.startandroid.interfaces.OfflineListener
import com.startandroid.utils.FileUtils
import com.startandroid.utils.I18n
import com.startandroid.utils.Utils
import java.lang.Integer.parseInt


class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    private var offline: SwitchPreference? = null
    private var languagePreference: ListPreference? = null
    private var webViewCore: Preference? = null

    private var mListener: OfflineListener = object : OfflineListener {
        override fun onProcess() {}
        override fun onCompleted() {
            Toast.makeText(requireContext(), R.string.offline_activated, Toast.LENGTH_LONG).show()
        }

        override fun onFailed() {
            FileUtils.deleteOffline(requireContext())
        }

        override fun onCanceled() {
            FileUtils.deleteOffline(requireContext())
        }
    }

    override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val activity = requireActivity()

        // Смена сервиса WebKit
        webViewCore = findPreference<Preference?>("webview_core")?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                if (Build.VERSION.SDK_INT >= 24) {
                    val intent = Intent(Settings.ACTION_WEBVIEW_SETTINGS)
                    if (intent.resolveActivity(activity.packageManager) != null) {
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.not_supported_on_your_device),
                        Toast.LENGTH_LONG
                    ).show()
                }
                true
            }
        }

        // Смена языка приложения
        languagePreference = findPreference<ListPreference?>("language")?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _: Preference?, p2: Any ->
                Preferences.languageType = parseInt(p2 as String)
                I18n.setLanguage(activity)
                startActivity(Intent(activity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                })
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            // Полноэкранный режим
            "fullscreen_mode" -> restartPerfect(requireActivity().intent)
            // Тип списка уроков
            "grid_mode" -> requireActivity().setResult(Activity.RESULT_OK)
            "offline" -> {
                if (!Preferences.offlineInstalled) {
                    val activity = requireActivity()
                    if (Utils.isNetworkAvailable()) {
                        val progressDialog = ProgressDialog(context)
                        progressDialog.setTitle("Downloading")
                        Offline(activity, mListener).execute()
                    } else {
                        offline?.isChecked = false
                        noConnectionError(activity)
                    }
                }
            }
        }
    }

    private fun restartPerfect(intent: Intent) {
        requireActivity().finish()
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
    }
}