package com.example.pokemon_application.features.pokemons_feed.presentation

sealed class PokemonsScreensViewState {
    data class Success(val items: List<PokemonRecyclerViewItem>) : PokemonsScreensViewState()
    data object Error : PokemonsScreensViewState()
    data object Loading : PokemonsScreensViewState()
}