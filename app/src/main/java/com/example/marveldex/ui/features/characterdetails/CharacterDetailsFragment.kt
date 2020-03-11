package com.example.marveldex.ui.features.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.example.marveldex.R
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

        characterName.text = args.heroName
        characterImage.load(args.heroImageURL)

        launch {
            val comics = viewModel.fetchHeroComics(args.heroID)

            if (comics.isNotEmpty()) {
                adapter = CharacterDetailsAdapter(comics)
                characterDetailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                characterDetailsRecyclerView.adapter = adapter

            } else {
                emptyTextView.visibility = View.VISIBLE
            }
            indeterminateBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}