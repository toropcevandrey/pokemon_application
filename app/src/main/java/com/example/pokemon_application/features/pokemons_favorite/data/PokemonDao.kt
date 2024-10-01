package com.example.pokemon_application.features.pokemons_favorite.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons")
    suspend fun getAllPokemonsFromFavoriteDB(): List<PokemonData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemonToFavoriteDB(pokemonData: PokemonData)

    @Query("DELETE FROM pokemons WHERE id = :id")
    suspend fun removeFromFavoriteDB(id:String)

    @Query("SELECT count() FROM pokemons WHERE id=:id")
    suspend fun isPokemonInFavoriteDB(id:String): Boolean
}