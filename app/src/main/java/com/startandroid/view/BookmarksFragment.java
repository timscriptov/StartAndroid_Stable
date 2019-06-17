package com.startandroid.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.startandroid.R;
import com.startandroid.adapters.BookmarksAdapter;
import com.startandroid.data.Bookmarks;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookmarksFragment extends DialogFragment {
    ArrayList<String> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = Bookmarks.getAllBookmarks();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                items.add(cursor.getString(1));
            }
        }
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.bookmarks_fragment, null);

        if (items.size() > 0) {
            RecyclerView rcview = view.findViewById(R.id.bookmarksList);
            rcview.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcview.setAdapter(new BookmarksAdapter(items, this, (MainView) getActivity()));
            rcview.setVisibility(View.VISIBLE);
        } else view.findViewById(R.id.no_bookmarks).setVisibility(View.VISIBLE);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.close, null)
                .create();
    }

}
