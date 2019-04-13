package com.startandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.startandroid.adapters.GridAdapter;
import com.startandroid.adapters.ListAdapter;
import com.startandroid.data.ListMode;
import com.startandroid.data.NightMode;
import com.startandroid.data.Preferences;
import com.startandroid.model.BaseActivity;
import com.startandroid.module.Ads;
import com.startandroid.module.AppUpdater;
import com.startandroid.module.Dialogs;
import com.startandroid.module.ListParser;
import com.startandroid.utils.Utils;
import com.startandroid.view.BookmarksFragment;
import com.startandroid.view.MainView;

import es.dmoral.toasty.Toasty;

import static com.startandroid.data.Constants.LK;
import static com.startandroid.data.Constants.MI;
import static com.startandroid.data.Constants.PREMIUM;

public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener, IBillingHandler, NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout adLayout;
    private BillingProcessor billing;
    private ListAdapter listAdapter;
    private GridAdapter gridAdapter;
    private Ads ads;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public boolean onQueryTextSubmit(String p1) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String p1) {
        if (listAdapter != null) {
            listAdapter.getFilter().filter(p1);
        } else gridAdapter.getFilter().filter(p1);
        return false;
    }

    @Override
    public void openLesson(String url, int position) {
        if (!Utils.isNetworkAvailable()) {
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        adLayout = findViewById(R.id.adLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (ListMode.getCurrentMode().equals(ListMode.Mode.GRID)) {
            GridView gridLessons = (GridView) getLayoutInflater().inflate(R.layout.grid_view, null);
            gridLessons.setAdapter(gridAdapter = new ListParser(this).getGridAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(gridLessons);
        } else {
            RecyclerView lessons = (RecyclerView) getLayoutInflater().inflate(R.layout.recycler_view, null);
            lessons.setLayoutManager(new LinearLayoutManager(this));
            lessons.setAdapter(listAdapter = new ListParser(this).getListAdapter());
            ((LinearLayout) findViewById(R.id.listContainer)).addView(lessons);
        }

        ads = new Ads();
        billing = new BillingProcessor(this, LK, MI, this);
        if(savedInstanceState == null)
        drawerLayout.openDrawer(GravityCompat.START);
        new AppUpdater(this).execute();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.continue_lesson).setVisible(Preferences.getBookmark() != null);
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(this);
        ((MenuBuilder) menu).setOptionalIconsVisible(true);

        if(NightMode.getCurrentMode() == NightMode.Mode.DAY) menu.findItem(R.id.day_night).setIcon(R.drawable.ic_night);

        menu.findItem(R.id.search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menu.findItem(R.id.continue_lesson).setVisible(false);
                menu.findItem(R.id.day_night).setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.findItem(R.id.continue_lesson).setVisible(true);
                menu.findItem(R.id.day_night).setVisible(true);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.day_night:
                if(NightMode.getCurrentMode() == NightMode.Mode.DAY){
                    NightMode.setMode(NightMode.Mode.NIGHT);
                    Preferences.setNightMode(true);
                }
                else {
                    NightMode.setMode(NightMode.Mode.DAY);
                    Preferences.setNightMode(false);
                }
                getDelegate().applyDayNight();
                break;
            case R.id.continue_lesson:
                if (Utils.isNetworkAvailable())
                    resumeLesson();
                else Dialogs.noConnectionError(this);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else {
            Toasty.info(this, getString(R.string.press_back_once_more)).show();
            time = System.currentTimeMillis();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        billing.handleActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IS_READ) {

            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);

                if (listAdapter != null) {
                    listAdapter.notifyItemChanged(position);
                } else gridAdapter.notifyDataSetChanged();
            }

            if (!Preferences.isRated()) Dialogs.rate(this);
            else if (!billing.isPurchased(PREMIUM)) {
                ads.showInsAd();
            }
        }
    }

    private void resumeLesson() {
        startActivityForResult(new Intent(this, LessonActivity.class).
                putExtra("url", Preferences.getBookmark()), REQUEST_CODE_IS_READ);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toasty.success(this, getString(R.string.premium_activated)).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        if (errorCode == Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            Toasty.error(this, getString(R.string.purchase_canceled)).show();
        }
    }

    @Override
    public void onBillingInitialized() {
        if (!billing.isPurchased(PREMIUM)) {
            adLayout.addView(ads.getBanner(this));
            ads.loadInterstitial(this);
            navigationView.getMenu().findItem(R.id.buy_premium).setVisible(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bookmarks:
                new BookmarksFragment().show(getSupportFragmentManager(), null);
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_CODE_SETTINGS);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.buy_premium:
                billing.purchase(this, PREMIUM, "premium");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
