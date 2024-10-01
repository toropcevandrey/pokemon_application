package com.example.pokemon_application.features.pokemons_feed.presentation

import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.CardViewPokemonBinding

class PokemonsFeedViewHolder(
    val binding: CardViewPokemonBinding,
    private val onClickListener: PokemonsFeedListAdapter.OnPokemonClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String, text: String, image: String, isFavorite: Boolean) {
        binding.tvPokemonName.text = text
        Glide.with(itemView)
            .load(image)
            .into(binding.ivPokemonImage)

        binding.cvPokemons.setOnClickListener {
            onClickListener.onPokemonClick(id)
        }

        val icon = if (isFavorite) {
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite)
        } else {
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_unfavorite)
        }

        binding.btnPokemonFavoriteButton.setImageDrawable(icon)

        binding.btnPokemonFavoriteButton.setOnClickListener {
            val a = isFavorite
            Log.d("cat", a.toString())
            onClickListener.onFavoriteClick(id)
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