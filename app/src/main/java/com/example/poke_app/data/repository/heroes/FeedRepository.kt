package com.example.poke_app.data.repository.heroes

import com.example.poke_app.data.PokeApiService
import com.example.poke_app.model.PokeCardsModel
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val pokeApiService: PokeApiService
) {
    suspend fun getAllPokeCardsFromApi(): PokeCardsModel = pokeApiService.getAllPokeCards()

    suspend fun getPokeByName(name: String): PokeCardsModel =
        pokeApiService.getPokeByName("name:$name")
}