package com.startandroid.entity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;
import com.startandroid.R;
import com.startandroid.data.Preferences;
import com.startandroid.interfaces.OfflineListener;
import com.startandroid.utils.ZipHelper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Offline extends AsyncTask<Void, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private final Activity settingsActivity;
    private final ProgressDialog progressDialog;
    private final OfflineListener mListener;

    public Offline(Activity settingsActivity, OfflineListener mListener) {
        this.settingsActivity = settingsActivity;
        progressDialog = new ProgressDialog(settingsActivity);
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onProcess();
        progressDialog.setTitle(settingsActivity.getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, settingsActivity.getString(android.R.string.cancel), (dialogInterface, i) -> {
            cancel(true);
            dialogInterface.dismiss();
            Preferences.setOffline(false);
        });
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            try {
                URL url = new URL("https://timscriptov.github.io/lessons/startandroid.zip");
                URLConnection connection = url.openConnection();

                progressDialog.setMax(connection.getContentLength());
                progressDialog.show();

                File offline = new File(settingsActivity.getFilesDir(), "offline.zip");
                File resourcesDir = new File(settingsActivity.getFilesDir(), "resources");

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                OutputStream outputStream = new FileOutputStream(offline);

                int count;
                byte[] data = new byte[1024];

                while ((count = inputStream.read(data)) != -1) {
                    progressDialog.incrementProgressBy(count);
                    outputStream.write(data, 0, count);
                }
                inputStream.close();
                outputStream.flush();

                ZipHelper.unpack(offline, resourcesDir);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (values[0] == -1) {
            progressDialog.setTitle("Unpacking");
        } else {
            progressDialog.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        if (bool) {
            mListener.onCompleted();
            progressDialog.dismiss();
            Preferences.setOffline(true);
            Preferences.setOfflineInstalled(true);
            Toast.makeText(settingsActivity, settingsActivity.getString(R.string.offline_activated), Toast.LENGTH_LONG).show();
        } else {
            mListener.onFailed();
            progressDialog.dismiss();
            Preferences.setOffline(false);
            Preferences.setOfflineInstalled(false);
        }
    }
}