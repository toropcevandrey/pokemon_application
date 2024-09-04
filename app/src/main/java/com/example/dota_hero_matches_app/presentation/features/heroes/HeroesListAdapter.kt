package com.example.dota_hero_matches_app.presentation.features.heroes

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dota_hero_matches_app.model.HeroesModel

class HeroesListAdapter: ListAdapter<HeroesModel, HeroesViewHolder>(HeroesDataComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.get(position).name)
    }
}