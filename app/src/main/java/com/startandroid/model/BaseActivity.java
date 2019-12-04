package com.startandroid.model;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.startandroid.MainActivity;
import com.startandroid.R;
import com.startandroid.data.NightMode;
import com.startandroid.data.ScreenMode;
import com.startandroid.utils.LocaleHelper;

import ru.svolf.melissa.sheet.SweetContentDialog;

public abstract class BaseActivity extends AppCompatActivity {
    static {
        NightMode.setMode(NightMode.getCurrentMode());
    }

    /**
     * Код текущего языка
     */
    private String lang = null;

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

    @Override
    public void onResume() {
        super.onResume();
        // Получаем теущий язык
        if (lang == null) {
            lang = LocaleHelper.getLanguage(this);
        }
        // Если язык из настроек не соответствует полученномму языку
        if (!LocaleHelper.getLanguage(this).equals(lang)) {
            // Новый контекст нужен для того, чтобы отобразить диалог изменения языка
            // в правильном формате
            Context newContext = LocaleHelper.onAttach(this);
            SweetContentDialog dialog = new SweetContentDialog(this);
            dialog.setTitle(newContext.getString(R.string.pref_sys_lang));
            dialog.setMessage(newContext.getString(R.string.lang_changed));
            dialog.setPositive(R.drawable.done, newContext.getString(android.R.string.ok), view -> restartApplication(BaseActivity.this));
            dialog.show();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        // Иначе не будут ресолвиться стринги из ресурсов, и половина аппы будет на языке системы,
        // а другая половина на языке, который выбран в настройках
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    /**
     * Рестарт процесса приложения. Нужно для того, чтобы языковые настройки изменились на новые
     * обычный {@link #finish()} тут не работает
     */
    protected static void restartApplication(Activity activity) {
        Intent mStartActivity = new Intent(activity, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(activity, mPendingIntentId, mStartActivity,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        activity.finish();
        System.exit(0);
    }

}
