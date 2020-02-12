package com.startandroid.module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;

import com.startandroid.App;
import com.startandroid.R;
import com.startandroid.data.Preferences;

import ru.svolf.melissa.sheet.SweetViewDialog;

public class Dialogs {
    public static void noConnectionError(final Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.no_connection_error, null);

        final SweetViewDialog dialog = new SweetViewDialog(context);
        dialog.setTitle(R.string.error);
        dialog.setView(v);
        dialog.setPositive(android.R.string.ok, v1 -> {
            dialog.cancel();
        });
        dialog.show();
    }

    public static void show(Context c, String text) {
        new AlertDialog.Builder(c)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
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

    public static void error(Context c, String text) {
        new AlertDialog.Builder(c)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(true)
                .create().show();
    }
}