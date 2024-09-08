package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import androidx.recyclerview.widget.DiffUtil

class PokemonsComparator : DiffUtil.ItemCallback<PokemonsViewData>() {
    override fun areItemsTheSame(oldItem: PokemonsViewData, newItem: PokemonsViewData): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonsViewData, newItem: PokemonsViewData): Boolean {
        return oldItem == newItem
    }
}