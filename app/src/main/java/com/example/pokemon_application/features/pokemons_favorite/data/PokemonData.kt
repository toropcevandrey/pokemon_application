package com.example.pokemon_application.features.pokemons_favorite.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonData(
    @PrimaryKey val id: String,
    val image: String,
    val name: String
)
