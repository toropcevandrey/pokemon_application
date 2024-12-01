package com.example.pokemon_application.features.pokemons_feed.presentation

import androidx.recyclerview.widget.DiffUtil

class PokemonsFeedComparator : DiffUtil.ItemCallback<PokemonRecyclerViewItem>() {
    override fun areItemsTheSame(
        oldItem: PokemonRecyclerViewItem, newItem: PokemonRecyclerViewItem
    ): Boolean {
        return when {
            oldItem is PokemonsScreenViewData && newItem is PokemonsScreenViewData && oldItem.id == newItem.id -> true
            oldItem is PokemonsScreenViewData && newItem is PokemonsScreenViewData && oldItem.id != newItem.id -> false
            oldItem is PokemonsScreenViewData && newItem is LoadingItem -> false
            oldItem is LoadingItem && newItem is PokemonsScreenViewData -> false
            oldItem is LoadingItem && newItem is LoadingItem -> true
            else -> {
                throw Exception("Unreacheable state")
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: PokemonRecyclerViewItem, newItem: PokemonRecyclerViewItem
    ): Boolean {
        return when {
            oldItem is PokemonsScreenViewData && newItem is PokemonsScreenViewData -> {
                oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.image == newItem.image &&
                        oldItem.isFavorite == newItem.isFavorite
            }

            oldItem is LoadingItem && newItem is LoadingItem -> {
                oldItem.isShow == newItem.isShow
            }

            else -> false
        }
    }

    override fun getChangePayload(
        oldItem: PokemonRecyclerViewItem, newItem: PokemonRecyclerViewItem
    ): Any? {
        return when {
            oldItem is PokemonsScreenViewData && newItem is PokemonsScreenViewData &&
                    oldItem.isFavorite != newItem.isFavorite -> {
                newItem.isFavorite
            }

            else -> null
        }
    }
}