package com.startandroid.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.startandroid.App;
import com.startandroid.BuildConfig;
import com.startandroid.R;
import com.startandroid.data.Preferences;
import com.startandroid.model.BaseActivity;

import ru.svolf.melissa.sheet.SweetContentDialog;
import ru.svolf.melissa.sheet.SweetViewDialog;

public class Dialogs extends BaseActivity {
    @SuppressLint("WrongConstant")
    public static void noConnectionError(final Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(40, 0, 40, 0);
        ll.setLayoutParams(layoutParams);
        final TextInputLayout til0 = new TextInputLayout(context);
        final AppCompatTextView message = new AppCompatTextView(context);
        message.setText(R.string.no_connection);
        til0.addView(message);
        ll.addView(til0);

        final SweetViewDialog dialog = new SweetViewDialog(context);
        dialog.setTitle(R.string.error);
        dialog.setView(ll);
        dialog.setPositive(android.R.string.ok, v1 -> {
            dialog.cancel();
        });
        dialog.show();
    }

    @SuppressLint("WrongConstant")
    public static void show(final Context context, String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(40, 0, 40, 0);
        ll.setLayoutParams(layoutParams);
        final TextInputLayout til0 = new TextInputLayout(context);
        final AppCompatTextView message = new AppCompatTextView(context);
        message.setText(text);
        til0.addView(message);
        ll.addView(til0);

        final SweetViewDialog dialog = new SweetViewDialog(context);
        //dialog.setTitle();
        dialog.setView(ll);
        dialog.setPositive(android.R.string.ok, v1 -> {
            dialog.cancel();
        });
        dialog.show();
    }

    public static void rate(final Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.rate, null);
        final RatingBar ratingBar = v.findViewById(R.id.rating_bar);

        SweetViewDialog dialog = new SweetViewDialog(context);
        dialog.setTitle(R.string.rate);
        dialog.setView(v);
        dialog.setPositive(R.string.rate, v1 -> {
            if (ratingBar.getRating() > 3) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.startandroid")));
                Preferences.setRated();
                dialog.cancel();
            } else {
                App.toast(R.string.thanks);
                App.preferences.edit().putBoolean("isRated", true).apply();
                dialog.cancel();
            }
        });
        dialog.setNegative(android.R.string.cancel, v1 -> {
            dialog.cancel();
        });
        dialog.show();
    }

    @SuppressLint("WrongConstant")
    public static void error(final Context context, String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(40, 0, 40, 0);
        ll.setLayoutParams(layoutParams);
        final TextInputLayout til0 = new TextInputLayout(context);
        final AppCompatTextView message = new AppCompatTextView(context);
        message.setText(text);
        til0.addView(message);
        ll.addView(til0);

        final SweetViewDialog dialog = new SweetViewDialog(context);
        //dialog.setTitle();
        dialog.setView(ll);
        dialog.setPositive(android.R.string.ok, v1 -> {
            dialog.cancel();
        });
        dialog.show();
    }
}