package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.CardViewPokemonBinding

class PokemonsFavoriteViewHolder(
    private val binding: CardViewPokemonBinding,
    private val onClickListener: PokemonsFavoriteListAdapter.OnPokemonClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String, text: String, image: String, favorite: Boolean) {
        binding.tvPokemonName.text = text
        Glide.with(itemView)
            .load(image)
            .into(binding.ivPokemonImage)
        binding.cvPokemons.setOnClickListener {
            onClickListener.onPokemonClick(id)
        }
        binding.btnPokemonFavoriteButton.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.ic_favorite
            )
        )
        binding.btnPokemonFavoriteButton.setOnClickListener {
            onClickListener.onFavoriteClick(id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickListener: PokemonsFavoriteListAdapter.OnPokemonClickListener
        ): PokemonsFavoriteViewHolder {
            return PokemonsFavoriteViewHolder(CardViewPokemonBinding.bind(parent), onClickListener)
        }
    }
}