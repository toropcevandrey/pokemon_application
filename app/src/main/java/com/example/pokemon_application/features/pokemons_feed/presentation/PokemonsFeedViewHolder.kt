package com.example.pokemon_application.features.pokemons_feed.presentation

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.CardViewPokemonBinding
import com.example.pokemon_application.databinding.LoadingFeedViewBinding

class PokemonsFeedViewHolder(
    val binding: CardViewPokemonBinding,
    private val onClickListener: PokemonsFeedListAdapter.OnPokemonClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String, text: String, image: String, isFavorite: Boolean) {
        binding.tvPokemonName.text = text
        Glide.with(binding.root.context)
            .load(image)
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPokemonImage)

        binding.cvPokemons.setOnClickListener {
            onClickListener.onPokemonClick(id)
        }

        binding.btnPokemonFavoriteButton.setImageDrawable(
            if (isFavorite) {
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite)
            } else {
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_unfavorite)
            }
        )

        binding.btnPokemonFavoriteButton.setOnClickListener {
            onClickListener.onFavoriteClick(id)
        }
    }

    fun updateFavoriteButton(isFavorite: Boolean) {
        binding.btnPokemonFavoriteButton.setImageDrawable(
            if (isFavorite) {
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite)
            } else {
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_unfavorite)
            }
        )
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

class PokemonsFeedLoadingViewHolder(val binding: LoadingFeedViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(isShow: Boolean) {
        binding.pbLoading.isVisible = isShow
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): PokemonsFeedLoadingViewHolder {
            return PokemonsFeedLoadingViewHolder(LoadingFeedViewBinding.bind(parent))
        }
    }
}