package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFavoriteBinding
import com.example.pokemon_application.databinding.FragmentPokemonsFeedBinding
import com.example.pokemon_application.features.pokemon_details.presentation.PokemonDetailsViewModel
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsFeedListAdapter
import com.example.pokemon_application.utils.view.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFavoriteFragment : Fragment(),PokemonsFavoriteListAdapter.OnPokemonClickListener {

    private lateinit var binding: FragmentPokemonsFavoriteBinding
    private val adapter: PokemonsFavoriteListAdapter by lazy { PokemonsFavoriteListAdapter(this) }
    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFavoriteBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun setupRecycle() {
        val spanCount = 2 // Количество столбцов
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing) // Отступы в пикселях
        val includeEdge = true // Включить отступы для крайних элементов
        val recyclerView: RecyclerView = binding.rvPokemonsFavorite

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    }

    override fun onPokemonClick(id: String) {
        TODO("Not yet implemented")
    }
}