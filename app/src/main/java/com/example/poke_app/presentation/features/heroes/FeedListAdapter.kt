package com.example.poke_app.presentation.features.heroes

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.poke_app.model.Data

class FeedListAdapter: ListAdapter<Data, HeroesViewHolder>(HeroesDataComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }
}