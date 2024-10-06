package com.example.pokemon_application.features.pokemon_details.data

import com.example.pokemon_application.features.pokemons_feed.data.PokemonsApiService
import javax.inject.Inject

class PokemonDetailsRepository @Inject constructor(
    private val pokemonsApiService: PokemonsApiService
) {
    suspend fun getPokemonDetailsByIdFromApi(id: String) = pokemonsApiService.getPokemonCardByIDFromAPI(id)
}