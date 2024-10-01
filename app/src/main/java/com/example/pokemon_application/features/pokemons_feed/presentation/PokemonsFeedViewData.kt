package com.example.pokemon_application.features.pokemons_feed.presentation

data class PokemonsFeedViewData(
    val id: String,
    val name: String,
    val image: String,
    var isFavorite: Boolean
)
