package com.example.marveldex.ui.features.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.example.marveldex.R
import com.example.marveldex.api.MarvelClientImpl
import com.example.marveldex.data.comics.MarvelComics
import com.example.marveldex.data.network.ResponseHandlerImpl
import com.example.marveldex.data.network.Status
import kotlinx.android.synthetic.main.character_details_fragment.*
import kotlinx.android.synthetic.main.item_character.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CharacterDetailsFragment : Fragment(), CoroutineScope by MainScope() {

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var adapter: CharacterDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
        viewModel.marvelClient = MarvelClientImpl
        viewModel.responseHandler = ResponseHandlerImpl

        renderCharacter()

        launch {
            val comicsResource = viewModel.fetchHeroComics(args.heroID)

            when (comicsResource.status) {
                Status.SUCCESS -> {
                    val comics = comicsResource.data!!.heroData.results
                    renderComics(comics)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), comicsResource.message, Toast.LENGTH_LONG).show()
                }
            }

            indeterminateBar.visibility = View.GONE
        }
    }

    private fun renderCharacter() {
        characterName.text = args.heroName
        characterImage.load(args.heroImageURL)
    }

    private fun renderComics(comics: List<MarvelComics>) {
        if (comics.isNotEmpty()) {
            adapter = CharacterDetailsAdapter(comics)
            characterDetailsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            characterDetailsRecyclerView.adapter = adapter

        } else {
            emptyTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}