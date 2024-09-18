package com.example.pokemon_application.presentation.features.pokemonsFeed

sealed class PokemonsFeedState {
    class Success(val pokemons: List<PokemonsFeedViewData>) : PokemonsFeedState()
    object Error : PokemonsFeedState()
    object Loading : PokemonsFeedState()
}