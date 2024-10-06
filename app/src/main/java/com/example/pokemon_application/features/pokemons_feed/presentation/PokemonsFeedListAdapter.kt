package com.example.pokemon_application.features.pokemons_feed.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemon_application.databinding.CardViewPokemonBinding

class PokemonsFeedListAdapter(
    private val onClickListener: OnPokemonClickListener
) : ListAdapter<PokemonsScreenViewData, PokemonsFeedViewHolder>(PokemonsFeedComparator()) {

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
        fun onFavoriteClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsFeedViewHolder {
        val binding =
            CardViewPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonsFeedViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PokemonsFeedViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.name, current.image, current.isFavorite)
    }

    override fun onBindViewHolder(
        holder: PokemonsFeedViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val current = getItem(position)

        if (payloads.isEmpty()) {
            holder.bind(current.id, current.name, current.image, current.isFavorite)
        } else {
            if (payloads[0] == true) {
                holder.bind(current.id, current.name, current.image, current.isFavorite)
            }
        }
    }
}