package com.startandroid.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.startandroid.App;
import com.startandroid.R;
import com.startandroid.utils.LessonUtils;
import com.startandroid.view.MainView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.startandroid.data.Constants.getResPath;

public class GridAdapter extends ArrayAdapter<String> {
    private SearchFilter filter;
    private ArrayList<String> items;
    private MainView mainView;

    public GridAdapter(ArrayList<String> items, MainView mainView) {
        super(App.getContext(), R.layout.lesson_grid_item, R.id.text, items);
        this.items = items;
        this.mainView = mainView;
        filter = new SearchFilter();
    }

    @NotNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @NotNull
    @Override
    public View getView(final int position, View convertView, @NotNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_grid_item, parent, false);

        final LinearLayout item = view.findViewById(R.id.lesson_item);
        final TextView itemText = view.findViewById(R.id.text);
        final ImageView checkMark = view.findViewById(R.id.checkMark);

        final String text = items.get(position);
        final int lessonNum = LessonUtils.getLessonNumberByTitle(text);
        final String url = getResPath() + "/lesson_" + lessonNum + ".html";

        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                mainView.openLesson(url, position);
            }
        });
        itemText.setText(text);
        checkMark.setVisibility(LessonUtils.isRead(lessonNum) ? View.VISIBLE : View.GONE);

        return view;
    }

    private class SearchFilter extends Filter {
        private ArrayList<String> items_backup = items;
        private ArrayList<String> filteredItems = new ArrayList<>();

        @Override
        protected Filter.FilterResults performFiltering(CharSequence p1) {
            filteredItems.clear();

            for (int x = 0; x < items_backup.size(); x++) {
                String query = p1.toString().toLowerCase();
                if (items_backup.get(x).toLowerCase().contains((query))) {
                    filteredItems.add(items_backup.get(x));
                }
            }
            return null;
        }

        @Override
        protected void publishResults(CharSequence p1, Filter.FilterResults p2) {
            items = filteredItems;
            notifyDataSetChanged();
        }

    }
}
