package com.example.pokemon_application.features.pokemons_favorite.data

import com.example.pokemon_application.features.pokemons_feed.data.PokemonsApiService
import javax.inject.Inject

class PokemonFavoriteRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    pokemonsApiService: PokemonsApiService
) {
    suspend fun getAllPokemons(): List<PokemonData> {
        return pokemonDao.getAllFavoritePokemons()
    }

    suspend fun deletePokemonFromFavorite(id: String) {
        pokemonDao.removeFromFavorite(id)
    }

    suspend fun addPokemonToFavorite(pokemonData: PokemonData) {
        pokemonDao.addToFavorite(pokemonData)
    }

    suspend fun ifPokemonFavorite(id: String): Boolean =
        pokemonDao.ifPokemonFavorite(id)

}