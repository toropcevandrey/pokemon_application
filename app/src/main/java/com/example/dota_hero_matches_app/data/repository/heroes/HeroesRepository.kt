package com.example.dota_hero_matches_app.data.repository.heroes

import com.example.dota_hero_matches_app.data.HeroesApiService
import com.example.dota_hero_matches_app.model.HeroesModel
import javax.inject.Inject

class HeroesRepository @Inject constructor(
    private val heroesApiService: HeroesApiService
) {
    suspend fun getHeroesFromApi(): HeroesModel = heroesApiService.getHeroes()
}