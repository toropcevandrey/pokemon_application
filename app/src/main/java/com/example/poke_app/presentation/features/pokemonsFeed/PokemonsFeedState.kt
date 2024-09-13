package com.example.poke_app.presentation.features.pokemonsFeed

sealed class PokemonsFeedState {
    class Success(val pokemons: List<PokemonsFeedViewData>) : PokemonsFeedState()
    object Error : PokemonsFeedState()
    object Loading : PokemonsFeedState()
}