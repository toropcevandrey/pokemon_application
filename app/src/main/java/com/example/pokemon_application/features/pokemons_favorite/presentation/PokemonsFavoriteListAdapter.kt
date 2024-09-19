package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemon_application.databinding.CardViewPokemonBinding

class PokemonsFavoriteListAdapter(
    private val onClickListener: OnPokemonClickListener
) :
    ListAdapter<PokemonsFavoriteViewData, PokemonsFavoriteViewHolder>(PokemonsFavoriteComparator()) {

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsFavoriteViewHolder {
        val binding =
            CardViewPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonsFavoriteViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PokemonsFavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.name, current.image)
    }
}