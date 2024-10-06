package com.example.pokemon_application.features.pokemons_favorite.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonData::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}