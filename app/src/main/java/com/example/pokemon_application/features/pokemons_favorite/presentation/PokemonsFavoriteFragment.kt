package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFavoriteBinding
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsFeedFragmentDirections
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreensViewState
import com.example.pokemon_application.utils.view.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFavoriteFragment : Fragment(), PokemonsFavoriteListAdapter.OnPokemonClickListener {

    private lateinit var binding: FragmentPokemonsFavoriteBinding
    private val adapter: PokemonsFavoriteListAdapter by lazy { PokemonsFavoriteListAdapter(this) }
    private val viewModel: PokemonsFavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFavoriteBinding.inflate(layoutInflater)

        initViews()
        onRefresh()
        setObservers()

        return binding.root
    }

    private fun initViews() {
        val spanCount = 2 // Количество столбцов
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing) // Отступы в пикселях
        val includeEdge = true // Включить отступы для крайних элементов
        val recyclerView: RecyclerView = binding.rvPokemonsFavorite

        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    }

    private fun onRefresh() {
        binding.svPokemonsFavorite.setOnRefreshListener {
            viewModel.loadPokemonsFavoriteFromDB()
            binding.svPokemonsFavorite.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.pokemonsFavoriteLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is PokemonsScreensViewState.Error
            val isSuccess = state is PokemonsScreensViewState.Success
            val isLoading = state is PokemonsScreensViewState.Loading

            binding.pbPokemonsFavorite.isVisible = isLoading
            binding.tvPokemonsFavoriteError.isVisible = isError
            binding.rvPokemonsFavorite.isVisible = isSuccess

            if (state is PokemonsScreensViewState.Success) {
                adapter.submitList(state.pokemons.toList())
            }
        }

        viewModel.observeOpenPokemonDetails.observe(viewLifecycleOwner) { id ->
            findNavController().navigate(
                PokemonsFeedFragmentDirections.toDetailsFragment(id)
            )
        }
    }

    override fun onPokemonClick(id: String) {
        viewModel.onPokemonClicked(id)
    }

    override fun onFavoriteClick(id: String) {
        viewModel.deletePokemonFromFavorite(id)
    }


}