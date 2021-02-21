package com.moisespedro.marveldex.ui.features.characterlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moisespedro.marveldex.R
import com.moisespedro.marveldex.data.heroes.MarvelHero
import com.moisespedro.marveldex.data.model.HeroDetail
import com.moisespedro.marveldex.data.network.Status
import com.moisespedro.marveldex.databinding.ListCharactersFragmentBinding
import com.moisespedro.marveldex.extensions.viewBinding
import com.moisespedro.marveldex.ui.features.characterlist.ListCharactersFragmentDirections.actionListCharactersFragmentToCharacterDetailsFragment
import com.moisespedro.marveldex.util.recyclerView.EndlessRecyclerViewScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListCharactersFragment : Fragment(R.layout.list_characters_fragment),
    CoroutineScope by MainScope() {

    private val binding by viewBinding(ListCharactersFragmentBinding::bind)
    private val viewModel: ListCharactersViewModel by viewModel()

    private var offset = 20
    private lateinit var adapter: ListCharactersAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ListCharactersAdapter(mutableListOf(), this::goToCharacterDetail)

        val linearLayoutManager = LinearLayoutManager(requireContext())

        loadInitialHeroes(linearLayoutManager)
        addScrollListener(linearLayoutManager)
    }

    private fun goToCharacterDetail(
        heroId: Int,
        heroName: String,
        heroImageURL: String,
        heroDescription: String
    ) {
        val heroDetail = HeroDetail(heroId, heroName, heroImageURL, heroDescription)
        val directions =
            actionListCharactersFragmentToCharacterDetailsFragment(heroDetail)
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
                    Toast.makeText(requireContext(), heroesResource.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
            binding.indeterminateBar.visibility = View.GONE
        }
    }

    private fun renderCharacterList(
        linearLayoutManager: LinearLayoutManager,
        heroes: List<MarvelHero>
    ) {
        binding.charactersRecyclerView.layoutManager = linearLayoutManager
        binding.charactersRecyclerView.adapter = adapter
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
        binding.charactersRecyclerView.addOnScrollListener(scrollListener)
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
