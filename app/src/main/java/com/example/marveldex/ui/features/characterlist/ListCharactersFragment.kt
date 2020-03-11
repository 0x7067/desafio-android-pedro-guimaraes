package com.example.marveldex.ui.features.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marveldex.R
import com.example.marveldex.data.MarvelHero
import kotlinx.android.synthetic.main.list_characters_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ListCharactersFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var viewModel: ListCharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_characters_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListCharactersViewModel::class.java)
        launch {
            val heroes = viewModel.fetchMarvelHeroes()
            val adapter = ListCharactersAdapter(heroes as MutableList<MarvelHero>) {Toast.makeText(requireContext(), "oi", Toast.LENGTH_LONG).show() }
            charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            charactersRecyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
