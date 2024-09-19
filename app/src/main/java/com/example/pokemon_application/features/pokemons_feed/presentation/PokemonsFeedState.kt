package com.example.pokemon_application.features.pokemons_feed.presentation

sealed class PokemonsFeedState {
    class Success(val pokemons: List<PokemonsFeedViewData>) : PokemonsFeedState()
    object Error : PokemonsFeedState()
    object Loading : PokemonsFeedState()
}