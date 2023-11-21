package com.startandroid.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputLayout
import com.startandroid.App.Companion.getPreferences
import com.startandroid.App.Companion.toast
import com.startandroid.R
import com.startandroid.activities.BaseActivity
import ru.svolf.melissa.sheet.SweetViewDialog

object Dialogs : BaseActivity() {
    @JvmStatic
    @SuppressLint("WrongConstant")
    fun noConnectionError(context: Context?) {
        val layoutParamz = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val message = AppCompatTextView(context!!).apply {
            setText(R.string.no_connection)
        }
        val til0 = TextInputLayout(context).apply {
            addView(message)
        }
        val ll = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 0, 40, 0)
            layoutParams = layoutParamz
            addView(til0)
        }
        val dialog = SweetViewDialog(context)
        dialog.setTitle(R.string.error)
        dialog.setView(ll)
        dialog.setPositive(android.R.string.ok) { v1: View? -> dialog.cancel() }
        dialog.show()
    }

    @JvmStatic
    fun rate(context: Context) {
        val v = LayoutInflater.from(context).inflate(R.layout.rate, null)
        val ratingBar = v.findViewById<RatingBar>(R.id.rating_bar)
        val dialog = SweetViewDialog(context)
        dialog.setTitle(R.string.rate)
        dialog.setView(v)
        dialog.setPositive(R.string.rate) { v1: View? ->
            if (ratingBar.rating > 3) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.startandroid")
                    )
                )
                Preferences.rated
                dialog.cancel()
            } else {
                toast(R.string.thanks)
                getPreferences().edit().putBoolean("isRated", true).apply()
                dialog.cancel()
            }
        }
        dialog.setNegative(android.R.string.cancel) { v1: View? -> dialog.cancel() }
        dialog.show()
    }

    @SuppressLint("WrongConstant")
    fun error(context: Context?, textz: String?) {
        val layoutParamz = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val message = AppCompatTextView(context!!).apply {
            text = textz
        }
        val til0 = TextInputLayout(context).apply {
            addView(message)
        }
        val ll = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 0, 40, 0)
            layoutParams = layoutParamz
            addView(til0)
        }

        val dialog = SweetViewDialog(context)
        //dialog.setTitle();
        dialog.setView(ll)
        dialog.setPositive(android.R.string.ok) { v1: View? -> dialog.cancel() }
        dialog.show()
    }
}