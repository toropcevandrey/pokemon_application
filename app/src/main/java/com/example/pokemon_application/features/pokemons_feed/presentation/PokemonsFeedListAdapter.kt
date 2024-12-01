package com.example.pokemon_application.features.pokemons_feed.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_application.databinding.CardViewPokemonBinding
import com.example.pokemon_application.databinding.LoadingFeedViewBinding

class PokemonsFeedListAdapter(
    private val onClickListener: OnPokemonClickListener
) : ListAdapter<PokemonRecyclerViewItem, RecyclerView.ViewHolder>(PokemonsFeedComparator()) {

    companion object {
        const val POKEMONS_LIST = 1
        const val LOADING = 2
    }

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
        fun onFavoriteClick(id: String)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonsScreenViewData -> POKEMONS_LIST
            is LoadingItem -> LOADING
            else -> throw Exception("Missing type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            POKEMONS_LIST -> {
                val binding =
                    CardViewPokemonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return PokemonsFeedViewHolder(binding, onClickListener)
            }

            LOADING -> {
                val binding =
                    LoadingFeedViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return PokemonsFeedLoadingViewHolder(binding)
            }

            else -> throw Exception("Missing type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonsFeedViewHolder -> {
                val item = getItem(position) as PokemonsScreenViewData
                holder.bind(
                    id = item.id,
                    text = item.name,
                    image = item.image,
                    isFavorite = item.isFavorite
                )
            }

            is PokemonsFeedLoadingViewHolder -> {
                val item = getItem(position) as LoadingItem
                holder.bind(isShow = item.isShow)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>
    ) {
        when (holder) {
            is PokemonsFeedViewHolder -> {
                val item = getItem(position) as PokemonsScreenViewData

                if (payloads.isNotEmpty()) {
                    val isFavorite = payloads[0] as? Boolean
                    if (isFavorite != null) {
                        holder.updateFavoriteButton(isFavorite)
                    }
                } else {
                    holder.bind(
                        id = item.id,
                        text = item.name,
                        image = item.image,
                        isFavorite = item.isFavorite
                    )
                }
            }

            is PokemonsFeedLoadingViewHolder -> {
                val item = getItem(position) as LoadingItem
                holder.bind(isShow = item.isShow)
            }
        }
    }
}