package com.example.pokemon_application.model

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("data")
    val pokemonApiModels: List<PokemonApiModel>,
    val page: Int,
    val pageSize: Int,
    val count: Int,
    val totalCount: Int,
)