package com.example.pokemon_application.features.pokemons_feed.presentation

import androidx.recyclerview.widget.DiffUtil

class PokemonsFeedComparator : DiffUtil.ItemCallback<PokemonsScreenViewData>() {
    override fun areItemsTheSame(
        oldItem: PokemonsScreenViewData, newItem: PokemonsScreenViewData
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PokemonsScreenViewData, newItem: PokemonsScreenViewData
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: PokemonsScreenViewData,
        newItem: PokemonsScreenViewData
    ): Any? {
        return if (oldItem.isFavorite != newItem.isFavorite) true else null
    }
}