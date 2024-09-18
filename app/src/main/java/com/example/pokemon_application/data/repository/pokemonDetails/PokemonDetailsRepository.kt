package com.example.pokemon_application.data.repository.pokemonDetails

import com.example.pokemon_application.data.PokemonsApiService
import javax.inject.Inject

class PokemonDetailsRepository @Inject constructor(
    private val pokemonsApiService: PokemonsApiService
) {
    suspend fun getPokemonDetailsByIdFromApi(id: String) = pokemonsApiService.getPokemonCardByID(id)
}