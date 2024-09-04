package com.example.dota_hero_matches_app.presentation.features.heroes

import androidx.recyclerview.widget.DiffUtil
import com.example.dota_hero_matches_app.model.HeroesModel

class HeroesDataComparator : DiffUtil.ItemCallback<HeroesModel>(){
    override fun areItemsTheSame(oldItem: HeroesModel, newItem: HeroesModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: HeroesModel, newItem: HeroesModel): Boolean {
        return oldItem == newItem
    }
}