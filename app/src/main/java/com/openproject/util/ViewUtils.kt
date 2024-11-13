package com.openproject.util

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ua.openproject.R

fun RecyclerView.showDividers() {
    val orientation = (layoutManager as LinearLayoutManager).orientation
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    ContextCompat.getDrawable(context, R.drawable.divider)?.let {
        dividerItemDecoration.setDrawable(it)
    }
    addItemDecoration(dividerItemDecoration)
}
