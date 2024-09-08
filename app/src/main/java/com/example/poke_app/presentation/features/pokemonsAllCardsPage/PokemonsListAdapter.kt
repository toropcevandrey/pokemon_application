package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class PokemonsListAdapter: ListAdapter<PokemonsViewData, PokemonsViewHolder>(PokemonsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsViewHolder {
        return PokemonsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokemonsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.image)
    }
}