package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFavoriteBinding
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreenViewData
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreensViewState
import com.example.pokemon_application.utils.view.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFavoriteFragment : Fragment(), PokemonsFavoriteListAdapter.OnPokemonClickListener {

    private lateinit var binding: FragmentPokemonsFavoriteBinding
    private val listAdapter: PokemonsFavoriteListAdapter by lazy { PokemonsFavoriteListAdapter(this) }
    private val viewModel: PokemonsFavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFavoriteBinding.inflate(layoutInflater)
        setupRecyclerView()
        onRefresh()
        setObservers()
        return binding.root
    }

    private fun setupRecyclerView() {
        val spanCount = 2
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val includeEdge = true
        binding.rvPokemonsFavorite.apply {
            adapter = listAdapter
            itemAnimator = null
            layoutManager = GridLayoutManager(activity, spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }

    private fun onRefresh() {
        binding.svPokemonsFavorite.setOnRefreshListener(ListenerF1())
    }

    inner class ListenerF1 : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            viewModel.loadPokemonsFavoriteFromInteractor()
            binding.svPokemonsFavorite.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.pokemonsFavoriteLiveData.observe(viewLifecycleOwner, ObserverF1())
        viewModel.observeOpenPokemonDetails.observe(viewLifecycleOwner, ObserverF2())
    }

    inner class ObserverF1 : Observer<PokemonsScreensViewState> {
        override fun onChanged(value: PokemonsScreensViewState) {
            val isError = value is PokemonsScreensViewState.Error
            val isSuccess = value is PokemonsScreensViewState.Success
            val isLoading = value is PokemonsScreensViewState.Loading

            binding.pbPokemonsFavorite.isVisible = isLoading
            binding.tvPokemonsFavoriteError.isVisible = isError
            binding.rvPokemonsFavorite.isVisible = isSuccess

            if (isSuccess) {
                val items = (value as PokemonsScreensViewState.Success).items
                listAdapter.submitList(items.filterIsInstance<PokemonsScreenViewData>())
            }
        }
    }

    inner class ObserverF2 : Observer<String> {
        override fun onChanged(value: String) {
            findNavController().navigate(
                PokemonsFavoriteFragmentDirections.actionPokemonsFavoriteFragmentToPokemonDetailsFragment(value)
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