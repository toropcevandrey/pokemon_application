package com.example.dota_hero_matches_app.model

import com.google.gson.annotations.SerializedName

data class HeroesModelItem(
    @SerializedName("attack_type")
    val attackType: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("legs")
    val legs: Int,
    @SerializedName("localized_name")
    val localizedName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("primary_attr")
    val primaryAttr: String,
    @SerializedName("roles")
    val roles: List<String>
)