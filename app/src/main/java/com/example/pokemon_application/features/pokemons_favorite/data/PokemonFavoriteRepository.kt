package com.example.pokemon_application.features.pokemons_favorite.data

import javax.inject.Inject

class PokemonFavoriteRepository @Inject constructor(
    private val pokemonDao: PokemonDao
) {
    suspend fun getAllPokemonsFromFavoriteDB(): List<PokemonData> {
        return pokemonDao.getAllPokemonsFromFavoriteDB()
    }

    suspend fun deletePokemonFromFavoriteDB(id: String) {
        pokemonDao.removeFromFavoriteDB(id)
    }

    suspend fun addPokemonToFavoriteDB(pokemonData: PokemonData) {
        pokemonDao.addPokemonToFavoriteDB(pokemonData)
    }

    suspend fun isPokemonInFavoriteDB(id: String): Boolean {
        return pokemonDao.isPokemonInFavoriteDB(id)
    }
}