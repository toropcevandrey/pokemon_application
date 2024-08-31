package com.example.dota_hero_matches_app.model

data class HeroesModelItem(
    val attack_type: String,
    val id: Int,
    val legs: Int,
    val localized_name: String,
    val name: String,
    val primary_attr: String,
    val roles: List<String>
)