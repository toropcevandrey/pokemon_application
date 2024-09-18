package com.example.pokemon_application.presentation.features.pokemonsFeed

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
import com.example.pokemon_application.presentation.view.GridSpacingItemDecoration
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFeedFragment : Fragment(), PokemonsFeedListAdapter.OnPokemonClickListener {

    private val viewModel: PokemonsFeedViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapter: PokemonsFeedListAdapter by lazy { PokemonsFeedListAdapter(this) }
    private lateinit var binding: FragmentPokemonsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFeedBinding.inflate(layoutInflater)
        initViews()
        setObservers()
        onRefresh()
        viewModel.getAllPokeCardsFromRepository()
        return binding.root
    }

    private fun initViews() {
        val spanCount = 2 // Количество столбцов
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing) // Отступы в пикселях
        val includeEdge = true // Включить отступы для крайних элементов

        recyclerView = binding.rvPokemonsFeed
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    }

    private fun onRefresh() {
        binding.svPokemonsFeed.setOnRefreshListener {
            viewModel.getAllPokeCardsFromRepository()
            binding.svPokemonsFeed.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.pokemonsFeedLiveData.observe(viewLifecycleOwner) { state ->
            val isError = state is PokemonsFeedState.Error
            val isSuccess = state is PokemonsFeedState.Success
            val isLoading = state is PokemonsFeedState.Loading

            binding.pbPokemonsFeed.isVisible = isLoading
            binding.tvPokemonsFeedError.isVisible = isError
            binding.rvPokemonsFeed.isVisible = isSuccess

            if (state is PokemonsFeedState.Success) {
                adapter.submitList(state.pokemons)
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
}