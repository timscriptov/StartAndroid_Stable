package com.startandroid.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import com.startandroid.App;
import com.startandroid.BuildConfig;
import com.startandroid.R;

import org.w3c.dom.Document;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AppUpdater extends AsyncTask<Void, Void, Void> {
    private String version_name;
    private int version_code;
    private String release_notes;
    private String download_link;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public AppUpdater(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://mcal-llc.github.io/sa/config/update.xml");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            con.disconnect();

            version_name = doc.getElementsByTagName("version_name").item(0).getTextContent();
            version_code = Integer.parseInt(doc.getElementsByTagName("version_code").item(0).getTextContent());
            release_notes = doc.getElementsByTagName("release_notes").item(0).getTextContent();
            download_link = doc.getElementsByTagName("download_link").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try {
            if (version_code > BuildConfig.VERSION_CODE) {
                updateApp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateApp() {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.version_available) + " " + version_name)
                .setMessage(release_notes)
                .setPositiveButton(R.string.update, (p1, p2) -> {
                    App.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(download_link)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    System.exit(0);
                })
                .setCancelable(false)
                .create()
                .show();
    }
}