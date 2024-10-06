package com.example.pokemon_application.features.pokemons_feed.presentation

sealed class PokemonsScreensViewState {
    data class Success(val pokemons: List<PokemonsScreenViewData>) : PokemonsScreensViewState()
    object Error : PokemonsScreensViewState()
    object Loading : PokemonsScreensViewState()
}