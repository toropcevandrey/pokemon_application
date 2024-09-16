package com.example.pokemon_application.presentation.features.pokemonsFeed

import androidx.recyclerview.widget.DiffUtil

class PokemonsFeedComparator : DiffUtil.ItemCallback<PokemonsFeedViewData>() {
    override fun areItemsTheSame(oldItem: PokemonsFeedViewData, newItem: PokemonsFeedViewData): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonsFeedViewData, newItem: PokemonsFeedViewData): Boolean {
        return oldItem == newItem
    }
}