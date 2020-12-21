package com.startandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.startandroid.data.ScreenMode;
import com.startandroid.utils.I18n;

import org.jetbrains.annotations.NotNull;

public abstract class BaseActivity extends AppCompatActivity {

    protected final int REQUEST_CODE_SETTINGS = 0;
    protected final int REQUEST_CODE_IS_READ = 1;
    protected long time = System.currentTimeMillis();
    private ScreenMode.Mode screenMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        I18n.setLanguage(this);

        screenMode = ScreenMode.getCurrentMode();

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

        if (requestCode == REQUEST_CODE_SETTINGS & resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
