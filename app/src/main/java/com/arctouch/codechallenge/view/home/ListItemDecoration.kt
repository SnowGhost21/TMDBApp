package com.arctouch.codechallenge.view.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.view.util.dpToPx

class ListItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = dpToPx(view.resources.displayMetrics, 16f)
        outRect.left = dpToPx(view.resources.displayMetrics, 16f)
        outRect.right = dpToPx(view.resources.displayMetrics, 16f)
    }
}