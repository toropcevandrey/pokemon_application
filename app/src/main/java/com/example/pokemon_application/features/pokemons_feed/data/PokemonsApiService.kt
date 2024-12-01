package com.example.pokemon_application.features.pokemons_feed.data

import com.example.pokemon_application.features.pokemon_details.data.model.PokemonsDetailsResponse
import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonsApiService {
    @GET("cards/"+"?3ac80715-ca5b-4d50-9037-1af7ccb25d11")
    suspend fun getAllPokemonsFromAPI(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 16,
    ): PokemonsResponse

    @GET("cards/{id}")
    suspend fun getPokemonCardByIDFromAPI(
        @Path("id") id: String,
    ): PokemonsDetailsResponse

    @GET("cards/?")
    suspend fun getPokemonByNameFromAPI(
        @Query("q", encoded = true) name: String,
    ): PokemonsResponse
}