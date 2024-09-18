package com.example.pokemon_application.presentation.features.pokemonsFeed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon_application.databinding.CardViewPokemonBinding

class PokemonsFeedViewHolder(
    val binding: CardViewPokemonBinding,
    private val onClickListener: PokemonsFeedListAdapter.OnPokemonClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String, text: String, image: String) {
        binding.tvPokemonName.text = text
        Glide.with(itemView)
            .load(image)
            .into(binding.ivPokemonImage)

        binding.cvPokemons.setOnClickListener {
            onClickListener.onPokemonClick(id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickListener: PokemonsFeedListAdapter.OnPokemonClickListener
        ): PokemonsFeedViewHolder {
            return PokemonsFeedViewHolder(CardViewPokemonBinding.bind(parent), onClickListener)
        }
    }
}