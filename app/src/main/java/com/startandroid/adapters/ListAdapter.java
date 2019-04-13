package com.startandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.startandroid.R;
import com.startandroid.data.Constants;
import com.startandroid.utils.Utils;
import com.startandroid.view.MainView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.startandroid.data.Constants.RES_PATH;
import static com.startandroid.utils.LessonUtils.getLessonNumberByTitle;
import static com.startandroid.utils.LessonUtils.isRead;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private SearchFilter filter;
    private ArrayList<String> items;
    private MainView mainView;

    public ListAdapter(ArrayList<String> items, MainView mainView) {
        this.items = items;
        this.mainView = mainView;
        filter = new SearchFilter();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NotNull final ListAdapter.ViewHolder holder, final int position) {
        final String text = items.get(position);
        final int number = getLessonNumberByTitle(text);
        final String url = RES_PATH + "/lesson_" + number + ".html";

        try {
            if (getClass().forName(Constants.ANTIPATCH) != null||
                    getClass().forName(Constants.ANTIPATCH1) != null ||
                    getClass().forName(Constants.ANTIPATCH2) != null ||
                    getClass().forName(Constants.ANTIPATCH3) != null) return;
        } catch (ClassNotFoundException e){
            holder.itemText.setText(text);
            holder.item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1) {
                    if(!Utils.isNetworkAvailable()){
                        return;
                    }
                    mainView.openLesson(url, holder.getAdapterPosition());
                }
            });
        }

        // ставим галочку если урок прочитанный
        holder.checkMark.setVisibility(isRead(number) ? View.VISIBLE : View.GONE);
    }

    @NotNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int p2) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_item, parent, false);
        return new ViewHolder(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView itemText;
        ImageView checkMark;

        ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.lesson_item);
            itemText = view.findViewById(R.id.text);
            checkMark = view.findViewById(R.id.checkMark);
        }
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