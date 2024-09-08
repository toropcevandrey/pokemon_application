package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poke_app.R
import com.example.poke_app.databinding.FragmentPokemonsBinding
import com.example.poke_app.presentation.view.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFragment : Fragment() {

    private val viewModel: PokemonsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapter: PokemonsListAdapter by lazy { PokemonsListAdapter() }
    private lateinit var binding: FragmentPokemonsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsBinding.inflate(layoutInflater)
        initViews()
        setObservers()
        viewModel.getAllPokeCardsFromRepository()
        return binding.root
    }

    private fun initViews() {
        val spanCount = 2 // Количество столбцов
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing) // Отступы в пикселях
        val includeEdge = true // Включить отступы для крайних элементов

        recyclerView = binding.rvHeroes
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
    }

    private fun setObservers() {
        viewModel.pokemonsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonsState.Error -> TODO("Добавить в разметку стейт ошибки")
                is PokemonsState.Loading -> TODO("Добавить в разметку стейт загрузки")
                is PokemonsState.Success -> adapter.submitList(state.pokemons)
            }
        }
    }
}