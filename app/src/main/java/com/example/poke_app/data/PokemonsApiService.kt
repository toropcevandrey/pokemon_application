package com.example.poke_app.data

import com.example.poke_app.model.PokemonsResponse
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
    ): PokemonsResponse

    @GET("cards/?")
    suspend fun getPokemonByName(
        @Query("q", encoded = true) name: String,
    ): PokemonsResponse
}