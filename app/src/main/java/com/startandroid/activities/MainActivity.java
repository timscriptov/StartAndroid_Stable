package com.startandroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.startandroid.BuildConfig;
import com.startandroid.R;
import com.startandroid.adapters.ListAdapter;
import com.startandroid.data.Dialogs;
import com.startandroid.data.Preferences;
import com.startandroid.entity.AppUpdaterCoroutine;
import com.startandroid.fragments.BookmarksFragment;
import com.startandroid.interfaces.MainView;
import com.startandroid.module.ListParser;
import com.startandroid.utils.Utils;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import ru.svolf.melissa.MainMenuAdapter;
import ru.svolf.melissa.MainMenuItem;
import ru.svolf.melissa.MainMenuItems;
import ru.svolf.melissa.sheet.SweetContentDialog;

import static com.anjlab.android.iab.v3.Constants.BILLING_RESPONSE_RESULT_USER_CANCELED;
import static com.startandroid.data.Constants.LK;
import static com.startandroid.data.Constants.PREMIUM;

public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener, IBillingHandler {

    private BillingProcessor billing;
    private ListAdapter listAdapter;
    private BottomSheetBehavior sheetBehavior;
    private boolean isPremium;
    private SearchView sv;

    @Override
    public boolean onQueryTextSubmit(String p1) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String p1) {
        listAdapter.getFilter().filter(p1);
        return false;
    }

    @Override
    public void openLesson(String url, int position) {
        if (!Preferences.getOffline() & !Utils.isNetworkAvailable()) {
            Dialogs.noConnectionError(this);
            return;
        }
        startActivityForResult(new Intent(this, LessonActivity.class)
                .putExtra("url", url)
                .putExtra("position", position), REQUEST_CODE_IS_READ);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();

        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomView));
        sv = findViewById(R.id.search_bar);

        RecyclerView lessons = (RecyclerView) getLayoutInflater().inflate(R.layout.recycler_view, null);

        if (Preferences.isInGridMode()) {
            lessons.setLayoutManager(new GridLayoutManager(this, 3));
            lessons.setAdapter(listAdapter = new ListParser(this).getListAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(lessons);
        } else {
            lessons.setLayoutManager(new LinearLayoutManager(this));
            lessons.setAdapter(listAdapter = new ListParser(this).getListAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(lessons);
        }

        setupSearchView();
        setupBottomSheet();

        billing = new BillingProcessor(this, LK, this);
        if (savedInstanceState == null)
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        isPremium = getIntent().getBooleanExtra("isPremium", false);
    }

    private void update() {
        AppUpdaterCoroutine updater = new AppUpdaterCoroutine();
        updater.with(this);
        updater.execute();
    }

    @Override
    public void onBackPressed() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            if (time + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else {
                Toasty.info(this, getString(R.string.press_back_once_more)).show();
                time = System.currentTimeMillis();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        billing.handleActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IS_READ) {

            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);

                listAdapter.notifyItemChanged(position);
            }

            //if (!Preferences.isRated()) {
            //    Dialogs.rate(this);
            //} else if (!billing.isPurchased(PREMIUM)) {
            //}
        }
    }

    private void resumeLesson() {
        startActivityForResult(new Intent(this, LessonActivity.class).
                putExtra("url", Preferences.getBookmark()), REQUEST_CODE_IS_READ);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toasty.success(this, getString(R.string.p_a)).show();// premium_activated
        // FIXME: Рефрешнуть адаптер
        setupBottomSheet();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        if (errorCode == BILLING_RESPONSE_RESULT_USER_CANCELED) {
            Toasty.error(this, getString(R.string.purchase_canceled)).show();
        }
    }

    @Override
    public void onBillingInitialized() {
        if (!billing.isPurchased(PREMIUM)) {
            // FIXME: Рефрешнуть адаптер
            setupBottomSheet();
        }
    }

    private void setupBottomSheet() {
        TextView caption = findViewById(R.id.caption);
        RecyclerView recycler = findViewById(R.id.list);
        if (recycler.getAdapter() != null) {
            recycler.setAdapter(null);
        }

        ArrayList<MainMenuItem> menuItems = new ArrayList<>();

        caption.setText(R.string.caption_lessons);
        if (Preferences.getBookmark() != null) {
            menuItems.add(new MainMenuItem(R.drawable.bookmark, "#fad805", getString(R.string.continue_lesson), MainMenuItems.CONTINUE));
        }
        menuItems.add(new MainMenuItem(R.drawable.star_bookmark, "#fdd835", getString(R.string.bookmarks), MainMenuItems.BOOKMARKS));
        menuItems.add(new MainMenuItem(R.drawable.settings, "#546e7a", getString(R.string.settings), MainMenuItems.SETTINGS));
        menuItems.add(new MainMenuItem(R.drawable.cash_multiple, "#43a047", getString(R.string.p), MainMenuItems.PREMIUM));
        menuItems.add(new MainMenuItem(R.drawable.information, "#3949ab", getString(R.string.about), MainMenuItems.ABOUT));
        menuItems.add(new MainMenuItem(R.drawable.exit, "#e53935", getString(R.string.exit), MainMenuItems.EXIT));

        MainMenuAdapter adapter = new MainMenuAdapter(menuItems);
        adapter.setItemClickListener((menuItem, position) -> {
            switch (menuItem.getAction()) {
                case MainMenuItems.BOOKMARKS: {
                    new BookmarksFragment().show(getSupportFragmentManager(), null);
                    break;
                }
                case MainMenuItems.ABOUT: {
                    showAboutSheet();
                    break;
                }
                case MainMenuItems.SETTINGS: {
                    startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class).putExtra("isPremium", billing.isPurchased(PREMIUM)), REQUEST_CODE_SETTINGS);
                    break;
                }
                case MainMenuItems.EXIT: {
                    finish();
                    break;
                }
                case MainMenuItems.PREMIUM: {
                    billing.purchase(MainActivity.this, PREMIUM);
                    break;
                }
                case MainMenuItems.CONTINUE: {
                    //if (isOffline() && Utils.isNetworkAvailable()) {
                    resumeLesson();
                    //} else {
                    //    Dialogs.noConnectionError(this);
                    //}
                    break;
                }
            }
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        recycler.setAdapter(adapter);
    }

    private void setupSearchView() {
        sv.setOnQueryTextListener(this);

        findViewById(R.id.button_night).setOnClickListener(view -> {
            if (Preferences.isInNightMode()) {
                Preferences.setNightMode(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                getDelegate().applyDayNight();
            } else {
                Preferences.setNightMode(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                getDelegate().applyDayNight();
            }
            getDelegate().applyDayNight();
        });
    }

    public void showAboutSheet() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(40, 0, 40, 0);
        ll.setLayoutParams(layoutParams);
        final TextInputLayout til0 = new TextInputLayout(this);
        final AppCompatTextView message = new AppCompatTextView(this);
        message.setGravity(1);
        message.setText(R.string.copyright);
        til0.addView(message);
        ll.addView(til0);

        final SweetContentDialog dialog = new SweetContentDialog(this);
        dialog.setTitle(this.getString(R.string.app_name) + " v." + BuildConfig.VERSION_NAME);
        dialog.setView(ll);
        dialog.addAction(R.drawable.star, this.getString(R.string.rate), view -> {
            Dialogs.rate(this);
            dialog.cancel();
        });
        dialog.addAction(R.drawable.google_play, this.getString(R.string.more_apps), view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Иван Тимашков")));
            dialog.cancel();
        });
        dialog.addAction(R.drawable.web, getString(R.string.source_materials), view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://startandroid.ru/")));
            dialog.cancel();
        });
        dialog.show();
    }
}