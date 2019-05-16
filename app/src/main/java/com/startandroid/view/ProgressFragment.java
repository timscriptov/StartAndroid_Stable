package com.startandroid.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.startandroid.R;
import com.startandroid.model.ProgressDialog;

public class ProgressFragment extends DialogFragment implements ProgressDialog {
    private ProgressBar progressBar;
    private AsyncTask asyncTask;

    @Override
    public void bindAsyncTask(AsyncTask task) {
        asyncTask = task;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.progress, null);
        progressBar = root.findViewById(R.id.progressBar);

        return new AlertDialog.Builder(getActivity())
                .setView(root)
                .setTitle(getString(R.string.downloading))
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        if (asyncTask != null)
                            asyncTask.cancel(true);
                    }
                })
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public void setProgress(int progress) {
        progressBar.incrementProgressBy(progress);
    }

    @Override
    public void indeterminate(boolean x) {
        progressBar.setIndeterminate(x);
    }

    @Override
    public void setMax(int max) {
        progressBar.setMax(max);
    }
}
