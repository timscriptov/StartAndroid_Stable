package com.startandroid.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import es.dmoral.toasty.Toasty
import java.lang.Integer.parseInt


class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    private var offline: SwitchPreference? = null
    private var languagePreference: ListPreference? = null
    private var webViewCore: Preference? = null

    private var mListener: OfflineListener = object : OfflineListener {
        override fun onProcess() {}
        override fun onCompleted() {
            Toasty.success(context!!, R.string.offline_activated).show()
        }

        override fun onFailed() {
            FileUtils.deleteOffline(context!!)
        }

        override fun onCanceled() {
            FileUtils.deleteOffline(context!!)
        }
    }

    override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // Смена сервиса WebKit
        webViewCore = findPreference("webview_core")
        webViewCore!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { p1: Preference? ->
                if (Build.VERSION.SDK_INT >= 24) {
                    val intent = Intent(Settings.ACTION_WEBVIEW_SETTINGS)
                    if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
                        startActivity(intent)
                    }
                } else {
                    Toasty.success(
                        requireContext(),
                        getString(R.string.not_supported_on_your_device)
                    ).show()
                }
                true
            }

        // Смена языка приложения
        languagePreference = findPreference("language")
        languagePreference!!.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { p1: Preference?, p2: Any ->
                val type: Int = parseInt(p2 as String)
                Preferences.languageType = type
                activity?.let { I18n.setLanguage(it) }
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                true
            }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            // Полноэкранный режим
            "fullscreen_mode" -> restartPerfect(requireActivity().intent)
            // Тип списка уроков
            "grid_mode" -> requireActivity().setResult(Activity.RESULT_OK)
            "offline" -> {
                if(!Preferences.offlineInstalled) {
                    if (Utils.isNetworkAvailable()) {
                        try {
                            val progressDialog = ProgressDialog(context)
                            progressDialog.setTitle("Downloading")
                            Offline(activity, mListener).execute()
                        } finally {
                            restartPerfect(requireActivity().intent)
                        }
                    } else {
                        offline!!.isChecked = false
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