package com.example.pokemon_application.features.pokemons_feed.presentation

interface PokemonRecyclerViewItem

data class LoadingItem(val isShow: Boolean) : PokemonRecyclerViewItem

data class PokemonsScreenViewData(
    val id: String,
    val name: String,
    val image: String,
    val isFavorite: Boolean
) : PokemonRecyclerViewItem