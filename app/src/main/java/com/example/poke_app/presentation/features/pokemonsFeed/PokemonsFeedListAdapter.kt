package com.example.poke_app.presentation.features.pokemonsFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.poke_app.databinding.CardViewHeroesBinding

class PokemonsFeedListAdapter :
    ListAdapter<PokemonsFeedViewData, PokemonsFeedViewHolder>(PokemonsFeedComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsFeedViewHolder {
        val binding =
            CardViewHeroesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonsFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonsFeedViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.image)
    }
}