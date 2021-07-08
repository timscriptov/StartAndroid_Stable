package com.startandroid.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.startandroid.R
import com.startandroid.adapters.BookmarksAdapter
import com.startandroid.data.Bookmarks.allBookmarks
import com.startandroid.interfaces.MainView
import ru.svolf.melissa.sheet.SweetViewDialog
import java.util.*

class BookmarksFragment : BottomSheetDialogFragment() {
    private val items = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cursor = allBookmarks
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                items.add(cursor.getString(1))
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @SuppressLint("InflateParams") val view =
            requireActivity().layoutInflater.inflate(R.layout.bookmarks_fragment, null)
        if (items.size > 0) {
            val rcview: RecyclerView = view.findViewById(R.id.bookmarksList)
            rcview.layoutManager = LinearLayoutManager(activity)
            rcview.adapter = BookmarksAdapter(items, this, activity as MainView?)
            rcview.visibility = View.VISIBLE
        } else view.findViewById<View>(R.id.no_bookmarks).visibility = View.VISIBLE
        val dialog = SweetViewDialog(requireContext())
        dialog.setTitle(getString(R.string.bookmarks))
        dialog.setView(view)
        dialog.setPositive(android.R.string.cancel, null)
        return dialog
    }
}