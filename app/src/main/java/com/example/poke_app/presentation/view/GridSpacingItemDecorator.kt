package com.example.poke_app.presentation.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,       // Количество столбцов
    private val spacing: Int,          // Размер отступов
    private val includeEdge: Boolean   // Включать ли отступы для крайних элементов
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // Позиция элемента
        val column = position % spanCount // Колонка, в которой находится элемент

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // Отступ сверху для первого ряда
                outRect.top = spacing
            }
            outRect.bottom = spacing // Отступ снизу для всех элементов
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount

            if (position >= spanCount) {
                outRect.top = spacing // Отступ сверху для всех рядов, кроме первого
            }
        }
    }
}