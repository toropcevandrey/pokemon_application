package com.example.pokemon_application.features.pokemons_favorite.presentation

import androidx.recyclerview.widget.DiffUtil

class PokemonsFavoriteComparator : DiffUtil.ItemCallback<PokemonsFavoriteViewData>() {
    override fun areItemsTheSame(oldItem: PokemonsFavoriteViewData, newItem: PokemonsFavoriteViewData): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonsFavoriteViewData, newItem: PokemonsFavoriteViewData): Boolean {
        return oldItem == newItem
    }
}