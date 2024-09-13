package com.example.poke_app.presentation.features.pokemonsFeed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poke_app.databinding.CardViewHeroesBinding

class PokemonsFeedViewHolder(val binding: CardViewHeroesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String, image: String) {
        binding.tvPokemonName.text = text
        Glide.with(itemView)
            .load(image)
            .into(binding.ivPokemonImage)
    }
}