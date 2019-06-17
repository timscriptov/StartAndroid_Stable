package com.startandroid.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.startandroid.data.NightMode;
import com.startandroid.data.ScreenMode;

public abstract class BaseActivity extends AppCompatActivity {
    static {
        NightMode.setMode(NightMode.getCurrentMode());
    }

    protected final int REQUEST_CODE_SETTINGS = 0;
    protected final int REQUEST_CODE_IS_READ = 1;
    protected long time = System.currentTimeMillis();
    private ScreenMode.Mode screenMode;
    private NightMode.Mode nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenMode = ScreenMode.getCurrentMode();
        nightMode = NightMode.getCurrentMode();

        if (screenMode.equals(ScreenMode.Mode.FULLSCREEN)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && ScreenMode.getCurrentMode().equals(ScreenMode.Mode.FULLSCREEN)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!screenMode.equals(ScreenMode.getCurrentMode())) recreate();
        if (!nightMode.equals(NightMode.getCurrentMode())) recreate();

        if (requestCode == REQUEST_CODE_SETTINGS & resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
