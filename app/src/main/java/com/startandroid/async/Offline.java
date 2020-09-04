package com.startandroid.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.startandroid.R;
import com.startandroid.interfaces.OfflineListener;

import org.zeroturnaround.zip.ZipUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Offline extends AsyncTask<Void, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Activity settingsActivity;
    private ProgressDialog progressDialog;
    private OfflineListener mListener;

    public Offline(OfflineListener listener, Activity settingsActivity) {
        this.mListener = listener;
        this.settingsActivity = settingsActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onProcess();
        progressDialog = new ProgressDialog(settingsActivity);
        progressDialog.setTitle(settingsActivity.getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, settingsActivity.getString(android.R.string.cancel), (dialogInterface, i) -> {
            onCancelled();
        });
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int count;
        byte[] data = new byte[1024];

        try {
            URL url = new URL("https://timscriptov.github.io/lessons/startandroid.zip");
            URLConnection connection = url.openConnection();
            File offline = new File(settingsActivity.getFilesDir(), "offline.zip");
            File resourcesDir = new File(settingsActivity.getFilesDir(), "resources");

            progressDialog.setMax(connection.getContentLength());

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            OutputStream outputStream = new FileOutputStream(offline);

            while ((count = inputStream.read(data)) != -1) {
                progressDialog.incrementProgressBy(count);
                outputStream.write(data, 0, count);
            }
            inputStream.close();
            outputStream.flush();

            ZipUtil.unpack(offline, resourcesDir);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values[0] == -1) {
            progressDialog.setTitle(settingsActivity.getString(R.string.unpacking));
        } else {
            progressDialog.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        if (bool) {
            mListener.onCompleted();
        } else {
            mListener.onFailed();
        }
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        cancel(true);
        mListener.onCanceled();
        progressDialog.dismiss();
    }
}