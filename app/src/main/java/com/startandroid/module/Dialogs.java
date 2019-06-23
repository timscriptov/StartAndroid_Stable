package com.startandroid.module;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;

import com.startandroid.App;
import com.startandroid.R;
import com.startandroid.data.Preferences;

import static com.startandroid.data.Constants.RATE;
import com.startandroid.data.*;

public class Dialogs {
    public static void noConnectionError(Context c) {
        new AlertDialog.Builder(c)
                .setTitle(R.string.error)
                .setMessage(R.string.no_connection)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .create().show();
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

        new AlertDialog.Builder(context)
                .setView(v)
                .setPositiveButton(R.string.rate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        if(ratingBar.getRating() > 3) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(RATE)));
                            Preferences.setRated();
                        } else {
                            App.toast(R.string.thanks);
                            App.preferences.edit().putBoolean(Constants.IS_RATED, true).apply();
                        }
                    }
                })
                .create()
                .show();
    }

    public static void error(Context c, String text) {
        new AlertDialog.Builder(c)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(true)
                .create().show();
    }

}
