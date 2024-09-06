package com.example.poke_app.presentation.features.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poke_app.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapter: FeedListAdapter by lazy { FeedListAdapter() }
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        initViews()
        setObservers()
        viewModel.getAllPokeCardsFromRepository()
        return binding.root
    }

    private fun initViews() {
        recyclerView = binding.rvHeroes
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    private fun setObservers() {

        viewModel.feedLiveData.observe(viewLifecycleOwner) { item ->
            adapter.submitList(item)
        }
    }
}