package com.example.marveldex.ui.features.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldex.R
import com.example.marveldex.api.MarvelClientImpl
import com.example.marveldex.data.heroes.MarvelHero
import com.example.marveldex.data.network.ResponseHandlerImpl
import com.example.marveldex.data.network.Status
import com.example.marveldex.ui.features.characterlist.ListCharactersFragmentDirections.actionListCharactersFragmentToCharacterDetailsFragment
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
        viewModel.marvelClient = MarvelClientImpl
        viewModel.responseHandler = ResponseHandlerImpl

        adapter = ListCharactersAdapter(mutableListOf(), this::goToCharacterDetail)

        val linearLayoutManager = LinearLayoutManager(requireContext())

        loadInitialHeroes(linearLayoutManager)
        addScrollListener(linearLayoutManager)
    }

    private fun goToCharacterDetail(heroId: Int, heroName: String, heroImageURL: String) {
        val directions =
            actionListCharactersFragmentToCharacterDetailsFragment(heroId, heroName, heroImageURL)
        findNavController().navigate(directions)
    }

    private fun loadInitialHeroes(linearLayoutManager: LinearLayoutManager) {
        launch {
            val heroesResource = viewModel.fetchMarvelHeroes(0)
            when (heroesResource.status) {
                Status.SUCCESS -> {
                    val heroes = heroesResource.data!!.heroData.results
                    renderCharacterList(linearLayoutManager, heroes)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), heroesResource.message, Toast.LENGTH_LONG).show()
                }
            }
            indeterminateBar.visibility = View.GONE
        }
    }

    private fun renderCharacterList(
        linearLayoutManager: LinearLayoutManager,
        heroes: List<MarvelHero>
    ) {
        charactersRecyclerView.layoutManager = linearLayoutManager
        charactersRecyclerView.adapter = adapter
        adapter.setHeroesList(heroes)
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
        val heroesResource = viewModel.fetchMarvelHeroes(offset)
        when (heroesResource.status) {
            Status.SUCCESS -> {
                val heroes = heroesResource.data!!.heroData.results
                adapter.setHeroesList(heroes)
                offset += 20
            }
            Status.ERROR -> {
                Toast.makeText(requireContext(), heroesResource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
