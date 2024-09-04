package com.example.dota_hero_matches_app.data

import com.example.dota_hero_matches_app.model.HeroesModel
import com.example.dota_hero_matches_app.model.HeroesModelItem
import retrofit2.http.GET

interface HeroesApiService {
    @GET("heroes")
    suspend fun getHeroes(): HeroesModel
}