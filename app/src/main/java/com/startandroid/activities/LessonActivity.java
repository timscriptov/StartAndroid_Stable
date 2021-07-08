package com.startandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mcal.mcpelauncher.utils.AdsAdmob;
import com.startandroid.R;
import com.startandroid.data.BillingRepository;
import com.startandroid.data.Bookmarks;
import com.startandroid.data.Dialogs;
import com.startandroid.data.Preferences;
import com.startandroid.module.HtmlRenderer;
import com.startandroid.utils.FileReader;
import com.startandroid.utils.LessonUtils;
import com.startandroid.utils.Utils;
import com.startandroid.view.MCProgressBar;
import com.startandroid.view.NestedWebView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static com.startandroid.data.Constants.getResPath;
import static com.startandroid.utils.LessonUtils.getLessonNumberByUrl;
import static com.startandroid.utils.LessonUtils.isRead;

public class LessonActivity extends BaseActivity implements OnClickListener {
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout adLayout;
    private MCProgressBar progressBar;
    private NestedWebView webView;
    private FloatingActionButton prev_lesson, next_lesson, bookmark;
    private CollapsingToolbarLayout ctl;
    private int itemPosition;
    private boolean isPremium;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(@NotNull View p1) {
        switch (p1.getId()) {
            case R.id.bookmark_lesson:
                int num = getLessonNumberByUrl(webView.getUrl());

                if (Bookmarks.isBookmarked(num)) {
                    Bookmarks.remove(num);
                    Snackbar.make(webView, getString(R.string.removed_from_bookmarks, num), Snackbar.LENGTH_LONG).show();
                    bookmark.setImageResource(R.drawable.star_bookmark);
                } else if (Bookmarks.add(num, ctl.getTitle().toString())) {
                    Snackbar.make(webView, getString(R.string.added_to_bookmarks, num), Snackbar.LENGTH_LONG).show();
                    bookmark.setImageResource(R.drawable.star_bookmarked);
                }
                bookmark.hide();
                bookmark.show();
                break;
            case R.id.prev_lesson:
                if (!Preferences.getOffline() & !Utils.isNetworkAvailable()) {
                    Dialogs.noConnectionError(LessonActivity.this);
                    return;
                }
                new PageLoader(getResPath() + "/lesson_" + (getLessonNumberByUrl(webView.getUrl()) - 1) + ".html").execute();
                itemPosition--;
                break;
            case R.id.next_lesson:
                if (!Preferences.getOffline() & !Utils.isNetworkAvailable()) {
                    Dialogs.noConnectionError(LessonActivity.this);
                    return;
                }
                new PageLoader(getResPath() + "/lesson_" + (getLessonNumberByUrl(webView.getUrl()) + 1) + ".html").execute();
                itemPosition++;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        isPremium = BillingRepository.INSTANCE.isPremium();

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adLayout = findViewById(R.id.ad_view);
        if (!isPremium) {
            adLayout.addView(AdsAdmob.showBannerAd(this));
        }
        ctl = findViewById(R.id.collapsing_toolbar);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        progressBar = findViewById(R.id.progress_bar);
        prev_lesson = findViewById(R.id.prev_lesson);
        next_lesson = findViewById(R.id.next_lesson);
        bookmark = findViewById(R.id.bookmark_lesson);
        prev_lesson.setOnClickListener(this);
        next_lesson.setOnClickListener(this);
        bookmark.setOnClickListener(this);
        itemPosition = getIntent().getIntExtra("position", 0);

        new PageLoader(getIntent().getStringExtra("url")).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Preferences.setBookmark(webView.getUrl());
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (time + 15000 < System.currentTimeMillis()) {
                final int num = getLessonNumberByUrl(webView.getUrl());

                if (!isRead(num)) {
                    Snackbar.make(webView, R.string.mark_as_read, Snackbar.LENGTH_INDEFINITE).setAction(R.string.yes, p1 -> markAsRead(num)).show();
                } else super.onBackPressed();
            } else super.onBackPressed();
        }
        time = System.currentTimeMillis();
    }

    private void markAsRead(int num) {
        if (LessonUtils.markAsRead(num)) {
            Snackbar.make(webView, getString(R.string.marked_as_read, num), Snackbar.LENGTH_LONG).show();
            setResult(RESULT_OK, new Intent().putExtra("isRead", true).putExtra("position", itemPosition));
            AsyncTask.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private class WebClient extends WebViewClient {
        @SuppressLint("RestrictedApi")
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            prev_lesson.setVisibility(getLessonNumberByUrl(url) != 1 ? View.VISIBLE : View.GONE);
            next_lesson.setVisibility(getLessonNumberByUrl(url) != 187 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

            if (Bookmarks.isBookmarked(getLessonNumberByUrl(url))) {
                bookmark.setImageResource(R.drawable.star_bookmarked);
            } else bookmark.setImageResource(R.drawable.star_bookmark);

            bookmark.hide();
            bookmark.show();
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            ctl.setTitle(title);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PageLoader extends AsyncTask<Void, Void, Void> {

        private String mLink;
        private String html;

        private PageLoader(String link) {
            this.mLink = link;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if (!isPremium) {
                AdsAdmob.showInterestialAd(LessonActivity.this, null);
            }
            if (Preferences.getOffline()) {
                webView.loadDataWithBaseURL("file:///" + mLink, HtmlRenderer.renderHtml(FileReader.fromStorage(mLink)), "text/html", "UTF-8", "file:///" + mLink);
                cancel(true);
            }
        }

        @Override
        protected Void doInBackground(Void... strings) {
            html = HtmlRenderer.renderHtml(FileReader.fromUrl(mLink));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mLink += "#googtrans(ru|" + Preferences.getLang() + ")";

            webView.loadDataWithBaseURL(mLink, html, "text/html", "UTF-8", mLink);
        }
    }
}
