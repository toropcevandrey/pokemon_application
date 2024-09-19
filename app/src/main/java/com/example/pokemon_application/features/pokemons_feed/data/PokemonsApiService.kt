package com.example.pokemon_application.features.pokemons_feed.data

import com.example.pokemon_application.features.pokemon_details.data.model.PokemonsDetailsResponse
import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonsApiService {
    @GET("cards")
    suspend fun getAllPokemons(
        @Query("pageSize") pageSize: Int = 20,
    ): PokemonsResponse

    @GET("cards/{id}")
    suspend fun getPokemonCardByID(
        @Path("id") id: String,
    ): PokemonsDetailsResponse

    @GET("cards/?")
    suspend fun getPokemonByName(
        @Query("q", encoded = true) name: String,
    ): PokemonsResponse
}