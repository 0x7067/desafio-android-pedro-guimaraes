package com.example.marveldex.ui.features.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldex.R
import com.example.marveldex.util.recyclerView.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.list_characters_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class ListCharactersFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var viewModel: ListCharactersViewModel
    private var offset = 20
    private lateinit var adapter: ListCharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_characters_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListCharactersViewModel::class.java)
        adapter = ListCharactersAdapter(mutableListOf()) {Toast.makeText(requireContext(), "oi", Toast.LENGTH_LONG).show() }

        val linearLayoutManager = LinearLayoutManager(requireContext())

        loadInitialHeroes(linearLayoutManager)
        addScrollListener(linearLayoutManager)
    }

    private fun loadInitialHeroes(linearLayoutManager: LinearLayoutManager) {
        launch {
            val heroes = viewModel.fetchMarvelHeroes(0)
            charactersRecyclerView.layoutManager = linearLayoutManager
            charactersRecyclerView.adapter = adapter
            adapter.setHeroesList(heroes)
        }
    }

    private fun addScrollListener(linearLayoutManager: LinearLayoutManager) {
        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?
            ) {
                launch {
                    loadMoreHeroes()
                }
            }
        }
        charactersRecyclerView.addOnScrollListener(scrollListener)
    }

    private suspend fun loadMoreHeroes() {
        val heroes = viewModel.fetchMarvelHeroes(offset)
        adapter.setHeroesList(heroes)
        offset += 20
    }
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
