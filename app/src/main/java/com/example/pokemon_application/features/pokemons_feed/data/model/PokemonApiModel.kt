package com.example.pokemon_application.features.pokemons_feed.data.model

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    val id: String,
    @SerializedName("images")
    val pokemonImages: PokemonImages,
    val name: String,
)