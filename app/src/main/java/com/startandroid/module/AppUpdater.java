package com.startandroid.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.startandroid.App;
import com.startandroid.R;

import org.w3c.dom.Document;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.startandroid.data.Constants.PACKAGE_NAME;
import static com.startandroid.data.Constants.UPDATE_PATH;

public class AppUpdater extends AsyncTask<Void, Void, Void> {
    private String version_name;
    private int version_code;
    private String release_notes;
    private String download_link;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public AppUpdater(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(UPDATE_PATH);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            con.disconnect();

            version_name = doc.getElementsByTagName("version_name").item(0).getTextContent();
            version_code = Integer.parseInt(doc.getElementsByTagName("version_code").item(0).getTextContent());
            release_notes = doc.getElementsByTagName("release_notes").item(0).getTextContent();
            download_link = doc.getElementsByTagName("download_link").item(0).getTextContent();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try {
            if (version_code > App.getContext().getPackageManager().getPackageInfo(PACKAGE_NAME, PackageManager.GET_META_DATA).versionCode) {
                updateApp();
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
    }

    private void updateApp() {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.version_available) + " " + version_name)
                .setMessage(release_notes)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        App.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(download_link)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        System.exit(0);
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}

