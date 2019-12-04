package com.elmurzaev.fontpreference;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

import java.util.ArrayList;

public class FontPreference extends ListPreference {
    private static ArrayList<Font> fonts = new ArrayList<>();
    private static Font selectedFont;

    public FontPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        try{
            String[] assetFonts = context.getAssets().list("fonts");
            for (String font : assetFonts) {
                fonts.add(new Font(font));
            }
            if (fonts.isEmpty()) {
                throw new IllegalStateException("FontPreference could not find any fonts in the assets/fonts folder.");
            }
        }catch (Exception e){
            e.printStackTrace()
        }
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        super.onSetInitialValue(defaultValue);
        selectedFont = new Font(getPersistedString((String) defaultValue));
    }

    public static class FontDialogFragment extends ListPreferenceDialogFragment {
        @Override
        public void onDialogClosed(boolean positiveResult) {

        }

        @Override
        protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
        }
    }
}
