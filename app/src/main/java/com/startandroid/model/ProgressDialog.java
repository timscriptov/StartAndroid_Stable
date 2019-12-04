package com.startandroid.model;

import android.os.AsyncTask;

public interface ProgressDialog {

    void setProgress(int progress);

    void indeterminate(boolean x);

    void bindAsyncTask(AsyncTask task);

    void dismiss();

    void setMax(int max);
}