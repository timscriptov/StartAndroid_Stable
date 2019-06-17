package com.startandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.startandroid.data.Database;
import com.startandroid.view.SplashWizard;

import static com.startandroid.data.Preferences.isFirstLaunch;

public class SplashActivity extends FragmentActivity {
    Animation fade_in, fade_out;
    LinearLayout logo;
    FrameLayout settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        settings = findViewById(R.id.preferencesFrame);

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation p1) {
                new Database();
            }

            @Override
            public void onAnimationEnd(Animation p1) {
                logo.startAnimation(fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation p1) {

            }
        });

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation p1) {

            }

            @Override
            public void onAnimationEnd(Animation p1) {
                logo.setVisibility(View.GONE);

                if (isFirstLaunch()) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    SplashWizard sw = new SplashWizard();
                    ft.add(settings.getId(), sw).commit();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation p1) {

            }

        });

        logo.startAnimation(fade_in);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
