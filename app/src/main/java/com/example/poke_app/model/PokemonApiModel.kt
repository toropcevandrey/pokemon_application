package com.example.poke_app.model

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    val id: String,
    @SerializedName("images")
    val pokemonImages: PokemonImages,
    val name: String,
)