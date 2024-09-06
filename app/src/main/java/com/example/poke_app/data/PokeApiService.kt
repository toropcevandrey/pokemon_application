package com.example.poke_app.data

import com.example.poke_app.model.PokeCardsModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("cards/")
    suspend fun getAllPokeCards(): PokeCardsModel

    @GET("cards/{id}")
    suspend fun getPokeCardByID(
        @Path("id") id: String,
    ): PokeCardsModel

    @GET("cards/?")
    suspend fun getPokeByName(
        @Query("q", encoded = true) name: String,
    ): PokeCardsModel
}