package com.startandroid.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputLayout
import com.startandroid.App.Companion.getPreferences
import com.startandroid.R
import com.startandroid.activities.BaseActivity
import ru.svolf.melissa.sheet.SweetViewDialog

object Dialogs : BaseActivity() {
    @JvmStatic
    @SuppressLint("WrongConstant")
    fun noConnectionError(context: Context) {
        SweetViewDialog(context).apply {
            setTitle(R.string.error)
            setView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(40, 0, 40, 0)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                addView(TextInputLayout(context).apply {
                    addView(AppCompatTextView(context).apply {
                        setText(R.string.no_connection)
                    })
                })
            })
            setPositive(android.R.string.ok) { cancel() }
        }.show()
    }

    @JvmStatic
    fun rate(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.rate, null)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        SweetViewDialog(context).apply {
            setTitle(R.string.rate)
            setView(view)
            setPositive(R.string.rate) {
                if (ratingBar.rating > 3) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.startandroid")
                        )
                    )
                    Preferences.rated
                    cancel()
                } else {
                    Toast.makeText(context, R.string.thanks, Toast.LENGTH_LONG).show()
                    getPreferences().edit().putBoolean("isRated", true).apply()
                    cancel()
                }
            }
            setNegative(android.R.string.cancel) { cancel() }
        }.show()
    }
}
