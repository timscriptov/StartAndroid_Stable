package com.startandroid.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.startandroid.BuildConfig
import com.startandroid.R
import com.startandroid.data.Dialogs.noConnectionError
import com.startandroid.data.Dialogs.show
import com.startandroid.entity.OfflineCoroutine
import com.startandroid.interfaces.OfflineListener
import com.startandroid.utils.FileUtils
import com.startandroid.utils.Utils
import es.dmoral.toasty.Toasty
import java.io.File

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    private var isPremium = false
    private var offline: SwitchPreference? = null
    private var downloadResources: Preference? = null
    private var clearCache: Preference? = null

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
        isPremium = requireActivity().intent.getBooleanExtra("isPremium", false)
        offline = findPreference("offline")
        downloadResources = findPreference("download_offline")
        downloadResources!!.onPreferenceClickListener = Preference.OnPreferenceClickListener { p1: Preference? ->
            try {
                if (Utils.isNetworkAvailable()) {
                    if (isPremium || BuildConfig.DEBUG) {
                        OfflineCoroutine(mListener, requireActivity()).execute()
                    } else {
                        FileUtils.deleteOffline(requireContext())
                        show(activity, getString(R.string.only_prem))
                    }
                } else {
                    noConnectionError(activity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        }
        clearCache = findPreference("clear_cache")
        clearCache!!.onPreferenceClickListener = Preference.OnPreferenceClickListener { p1: Preference? ->
            Thread {
                FileUtils.deleteOffline(requireContext())
            }.start()
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
            "offline" -> if (offline!!.isChecked) {
                val resourcesDir = File(requireContext().filesDir, "resources")
                if (resourcesDir.exists()) {
                    restartPerfect(requireActivity().intent)
                } else {
                    offline!!.isChecked = false
                    Toasty.success(requireContext(), R.string.not_installed_offline).show()
                }
            }
            "fullscreen_mode" -> restartPerfect(requireActivity().intent)
            "grid_mode" -> requireActivity().setResult(Activity.RESULT_OK)
        }
    }

    private fun restartPerfect(intent: Intent) {
        requireActivity().finish()
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
    }
}