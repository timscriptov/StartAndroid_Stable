package com.elmurzaev.fontpreference;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;

public class FontPreferenceAdapter extends BaseAdapter {
    private ArrayList<Font> fonts;
    private Font selectedFont;

    public FontPreferenceAdapter(ArrayList<Font> fonts, Font selectedFont) {
        this.fonts = fonts;
        this.selectedFont = selectedFont;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.select_dialog_singlechoice, viewGroup, false);
        }
        CheckedTextView checkedTextView = (CheckedTextView) view;
        Font font = fonts.get(position);
        Typeface type = Typeface.createFromAsset(context.getAssets(), font.getPath());
        checkedTextView.setTypeface(type);
        checkedTextView.setText(font.getName());
        checkedTextView.setChecked(font.equals(selectedFont));
        return checkedTextView;
    }

    @Override
    public int getCount() {
        return fonts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
