package com.startandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.startandroid.R;
import com.startandroid.interfaces.MainView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.startandroid.data.Constants.getResPath;
import static com.startandroid.utils.LessonUtils.getLessonNumberByTitle;
import static com.startandroid.utils.LessonUtils.isRead;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private final SearchFilter filter;
    private final MainView mainView;
    private ArrayList<String> items;

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
    public void onBindViewHolder(final ListAdapter.@NotNull ViewHolder holder, final int position) {
        final String text = items.get(position);
        final int number = getLessonNumberByTitle(text);

        holder.itemText.setText(text);
        holder.item.setOnClickListener(p1 -> mainView.openLesson(getResPath() + "/lesson_" + number + ".html", holder.getAdapterPosition()));

        // ставим галочку если урок прочитанный
        holder.checkMark.setVisibility(isRead(number) ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int p2) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
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
        private final ArrayList<String> items_backup = items;
        private final ArrayList<String> filteredItems = new ArrayList<>();

        @Override
        protected Filter.@Nullable FilterResults performFiltering(CharSequence p1) {
            filteredItems.clear();
            for (String s : items_backup) {
                String query = p1.toString().toLowerCase();
                if (s.toLowerCase().contains((query))) {
                    filteredItems.add(s);
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
