package com.startandroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.*;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.startandroid.BuildConfig;
import com.startandroid.R;
import com.startandroid.adapters.ListAdapter;
import com.startandroid.data.Dialogs;
import com.startandroid.data.Preferences;
import com.startandroid.fragments.BookmarksFragment;
import com.startandroid.interfaces.MainView;
import com.startandroid.module.ListParser;
import com.startandroid.utils.Utils;
import org.jetbrains.annotations.NotNull;
import ru.svolf.melissa.MainMenuAdapter;
import ru.svolf.melissa.MainMenuItem;
import ru.svolf.melissa.MainMenuItems;
import ru.svolf.melissa.sheet.SweetContentDialog;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener {
    private ListAdapter listAdapter;
    private BottomSheetBehavior sheetBehavior;
    private SearchView sv;
    private BillingClient billingClient;

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

        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener((billingResult, list) -> {
                    if (list != null && billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        billingClient.acknowledgePurchase(
                                AcknowledgePurchaseParams.newBuilder()
                                        .setPurchaseToken(list.get(0).getPurchaseToken()).build(),
                                billingResult1 -> {
                                    Toast.makeText(this, "Premium activated!", Toast.LENGTH_LONG).show();
                                });
                    }
                })
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Log.e(getLocalClassName(), "Billing disconnected");
            }

            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, (billingResult1, list) -> {
                        if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            if (!list.isEmpty()) {
                                // Set Premium
                            }
                        }
                    });
                }
            }
        });

        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomView));
        sv = findViewById(R.id.search_bar);

        RecyclerView lessons = (RecyclerView) getLayoutInflater().inflate(R.layout.recycler_view, null);

        // Проверка типа списка уроков
        if (Preferences.getGridMode()) {
            // Список в одну колонку
            lessons.setLayoutManager(new GridLayoutManager(this, 3));
            lessons.setAdapter(listAdapter = new ListParser(this).getListAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(lessons);
        } else {
            // Список в три колонки
            lessons.setLayoutManager(new LinearLayoutManager(this));
            lessons.setAdapter(listAdapter = new ListParser(this).getListAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(lessons);
        }

        // Подключение метода поиска уроков
        setupSearchView();
        // Подключение метода меню
        setupBottomSheet();

        if (savedInstanceState == null) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onBackPressed() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            if (time + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, getString(R.string.press_back_once_more), Toast.LENGTH_LONG).show();
                time = System.currentTimeMillis();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IS_READ) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                listAdapter.notifyItemChanged(position);
            }
        }
    }

    private void resumeLesson() {
        startActivityForResult(new Intent(this, LessonActivity.class).
                putExtra("url", Preferences.getBookmark()), REQUEST_CODE_IS_READ);
    }

    // Метод меню на главной странице
    private void setupBottomSheet() {
        final TextView caption = findViewById(R.id.caption);
        caption.setVisibility(View.GONE);

        final RecyclerView recycler = findViewById(R.id.list);
        if (recycler.getAdapter() != null) {
            recycler.setAdapter(null);
        }

        final ArrayList<MainMenuItem> menuItems = new ArrayList<>();

        if (Preferences.getBookmark() != null) {
            menuItems.add(new MainMenuItem(R.drawable.bookmark, "#fad805", getString(R.string.continue_lesson), MainMenuItems.CONTINUE));
        }
        menuItems.add(new MainMenuItem(R.drawable.star_bookmark, "#fdd835", getString(R.string.bookmarks), MainMenuItems.BOOKMARKS));
        menuItems.add(new MainMenuItem(R.drawable.settings, "#546e7a", getString(R.string.settings), MainMenuItems.SETTINGS));
        menuItems.add(new MainMenuItem(R.drawable.cash_multiple, "#43a047", getString(R.string.p), MainMenuItems.PREMIUM));
        menuItems.add(new MainMenuItem(R.drawable.information, "#3949ab", getString(R.string.about), MainMenuItems.ABOUT));
        menuItems.add(new MainMenuItem(R.drawable.exit, "#e53935", getString(R.string.exit), MainMenuItems.EXIT));

        final MainMenuAdapter adapter = new MainMenuAdapter(menuItems);
        adapter.setItemClickListener((menuItem, position) -> {
            switch (menuItem.getAction()) {
                // Закладки
                case MainMenuItems.BOOKMARKS: {
                    new BookmarksFragment().show(getSupportFragmentManager(), null);
                    break;
                }
                // О приложении
                case MainMenuItems.ABOUT: {
                    showAboutSheet();
                    break;
                }
                // Настройки
                case MainMenuItems.SETTINGS: {
                    startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), REQUEST_CODE_SETTINGS);
                    break;
                }
                // Выход из приложения
                case MainMenuItems.EXIT: {
                    finish();
                    break;
                }
                // Покупка платных функций
                case MainMenuItems.PREMIUM: {
                    ArrayList<String> list = new ArrayList<>();
                    list.add("premium");
                    billingClient.querySkuDetailsAsync(
                            SkuDetailsParams.newBuilder()
                                    .setType(BillingClient.SkuType.INAPP)
                                    .setSkusList(list).build(),
                            (billingResult, list1) -> {
                                if (list1 != null && billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                    billingClient.launchBillingFlow(this,
                                            BillingFlowParams.newBuilder().setSkuDetails(list1.get(0)).build());
                                }
                            });
                    break;
                }
                // Продолжить чтение с последнего урока
                case MainMenuItems.CONTINUE: {
                    resumeLesson();
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
            if (Preferences.getNightMode()) {
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
        AppCompatTextView message = new AppCompatTextView(this);
        message.setGravity(1);
        message.setText(R.string.copyright);
        ll.addView(message);

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
        dialog.addAction(R.drawable.ic_privacy_police, getString(R.string.privacy_police), view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://timscriptov.ru/privacy/startandroid/index.html")));
            dialog.cancel();
        });
        dialog.addAction(R.drawable.web, getString(R.string.source_materials), view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://startandroid.ru/")));
            dialog.cancel();
        });
        dialog.show();
    }
}
