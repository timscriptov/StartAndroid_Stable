package com.startandroid.module;


import android.os.AsyncTask;
import android.preference.SwitchPreference;
import android.webkit.URLUtil;

import com.startandroid.model.ProgressDialog;

import org.zeroturnaround.zip.ZipUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.startandroid.data.Constants.PACKAGE_NAME;
import static com.startandroid.data.Constants.STARTANDROID_ZIP;

public class Offline extends AsyncTask<Void, Integer, Boolean> {
    private ProgressDialog progressDialog;
    private SwitchPreference offline;

    public Offline(ProgressDialog progressDialog, SwitchPreference offline) {
        this.progressDialog = progressDialog;
        this.offline = offline;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.bindAsyncTask(this);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL(STARTANDROID_ZIP);
            URLConnection connection = url.openConnection();
            connection.connect();

            String fileName = URLUtil.guessFileName(url.toString(), null, null);
            int fileLength = connection.getContentLength();
            progressDialog.setMax(fileLength);

            File offline = new File("data/data/" + PACKAGE_NAME + "/files/" + fileName);
            File resourcesDir = new File("data/data/" + PACKAGE_NAME + "/files/resources");

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            OutputStream outputStream = new FileOutputStream(offline);

            int count;
            byte[] data = new byte[1024];

            while ((count = inputStream.read(data)) != -1) {
                publishProgress(count);
                outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            ZipUtil.unpack(offline, resourcesDir);
            offline.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(Boolean bool) {
        offline.setChecked(false);
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        progressDialog.dismiss();
        if (!bool) {
            offline.setChecked(false);
        }
    }
}
