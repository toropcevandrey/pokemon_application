package com.example.pokemon_application.features.pokemons_feed.presentation

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
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pokemon_application.utils.view.GridSpacingItemDecoration
import com.example.pokemon_application.R
import com.example.pokemon_application.databinding.FragmentPokemonsFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFeedFragment : Fragment(), PokemonsFeedListAdapter.OnPokemonClickListener {

    private val viewModel: PokemonsFeedViewModel by viewModels<PokemonsFeedViewModel>()
    private val listAdapter: PokemonsFeedListAdapter by lazy { PokemonsFeedListAdapter(this) }
    private lateinit var binding: FragmentPokemonsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setObservers()
        onRefresh()
        rvAddOnScrollListener()
    }

    private fun setupRecyclerView() {
        binding.rvPokemonsFeed.apply {
            val spanCount = 2
            val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            val includeEdge = true
            adapter = listAdapter
            layoutManager = GridLayoutManager(activity, spanCount).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (listAdapter.getItemViewType(position)) {
                            PokemonsFeedListAdapter.LOADING -> spanCount
                            PokemonsFeedListAdapter.POKEMONS_LIST -> spanCount - 1
                            else -> {
                                spanCount
                            }
                        }
                    }
                }
                    scrollToPosition(viewModel.currentScrollPosition)
            }
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }

    private fun onRefresh() {
        binding.svPokemonsFeed.setOnRefreshListener(ListenerF())
    }

    inner class ListenerF : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            viewModel.loadPokemonsFeedFromInteractor()
            binding.svPokemonsFeed.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.pokemonsFeedStateLiveData.observe(viewLifecycleOwner, ObserverF1())
        viewModel.observeOpenPokemonDetails.observe(viewLifecycleOwner, ObserverF2())
    }

    inner class ObserverF1 : Observer<PokemonsScreensViewState> {
        override fun onChanged(value: PokemonsScreensViewState) {
            val isError = value is PokemonsScreensViewState.Error
            val isSuccess = value is PokemonsScreensViewState.Success
            val isLoading = value is PokemonsScreensViewState.Loading

            binding.pbPokemonsFeed.isVisible = isLoading
            binding.tvPokemonsFeedError.isVisible = isError
            binding.rvPokemonsFeed.isVisible = isSuccess

            if (isSuccess) {
                listAdapter.submitList(
                    (value as PokemonsScreensViewState.Success).items.toList()
                )

            }
        }
    }

    inner class ObserverF2 : Observer<String> {
        override fun onChanged(value: String) {
            findNavController().navigate(
                PokemonsFeedFragmentDirections.actionPokemonsFeedFragmentToPokemonDetailsFragment(value)
            )
        }
    }

    private fun rvAddOnScrollListener() {
        binding.rvPokemonsFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isLoaded = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoaded
                ) {
                    isLoaded = true
                    viewModel.loadNextPage()
                }
                isLoaded = false
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                viewModel.onScrollPositionChanged(layoutManager.findFirstVisibleItemPosition())

            }
        })
    }

    override fun onPokemonClick(id: String) {
        viewModel.onPokemonClicked(id)
    }

    override fun onFavoriteClick(id: String) {
        viewModel.onFavoriteClicked(id)
    }
}