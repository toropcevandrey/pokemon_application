package com.example.pokemon_application.features.pokemons_feed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_application.utils.view.GridSpacingItemDecoration
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFeedFragment : Fragment(), PokemonsFeedListAdapter.OnPokemonClickListener {

    private val viewModel: PokemonsFeedViewModel by viewModels()
    private val listAdapter: PokemonsFeedListAdapter by lazy { PokemonsFeedListAdapter(this) }
    private lateinit var binding: FragmentPokemonsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFeedBinding.inflate(layoutInflater)
        setupRecyclerView()
        setObservers()
        onRefresh()
        return binding.root
    }

    private fun setupRecyclerView() {
        val spanCount = 2
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val includeEdge = true
        binding.rvPokemonsFeed.apply {
            adapter = listAdapter
            layoutManager = GridLayoutManager(activity, spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }

    private fun onRefresh() {
        binding.svPokemonsFeed.setOnRefreshListener {
            viewModel.loadPokemonsFeedFromInteractor()
            binding.svPokemonsFeed.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.pokemonsFeedLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is PokemonsScreensViewState.Error
            val isSuccess = state is PokemonsScreensViewState.Success
            val isLoading = state is PokemonsScreensViewState.Loading

            binding.pbPokemonsFeed.isVisible = isLoading
            binding.tvPokemonsFeedError.isVisible = isError
            binding.rvPokemonsFeed.isVisible = isSuccess

            if (isSuccess) {
                listAdapter.submitList((state as PokemonsScreensViewState.Success).pokemons.toList())
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
        viewModel.onFavoriteClicked(id)
    }
}