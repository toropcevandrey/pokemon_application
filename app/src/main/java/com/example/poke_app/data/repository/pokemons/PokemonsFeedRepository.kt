package com.example.poke_app.data.repository.pokemons

import com.example.poke_app.data.PokemonsApiService
import com.example.poke_app.model.PokemonsResponse
import javax.inject.Inject

class PokemonsFeedRepository @Inject constructor(
    private val pokemonsApiService: PokemonsApiService
) {
    suspend fun getAllPokemonsFromApi(): PokemonsResponse = pokemonsApiService.getAllPokemons()

    suspend fun getPokemonsByName(name: String): PokemonsResponse =
        pokemonsApiService.getPokemonByName("name:$name")
}