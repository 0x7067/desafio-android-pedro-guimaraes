package com.moisespedro.marveldex.ui.features.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.moisespedro.marveldex.R
import kotlinx.android.synthetic.main.character_details_fragment.*
import kotlinx.android.synthetic.main.item_character.*

class CharacterDetailsFragment : Fragment() {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        renderCharacter()
    }

    private fun renderCharacter() {
        characterName.text = args.heroDetail.name
        characterImage.load(args.heroDetail.imageUrl)
        renderCharacterDescription()
        buttonComic.setOnClickListener {
            val directions =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToHeroMostExpensiveComicFragment(
                    args.heroDetail.id
                )
            findNavController().navigate(directions)
        }
    }

    private fun renderCharacterDescription() {
        characterDescription.visibility = View.VISIBLE
        if (args.heroDetail.description.isNotEmpty()) {
            characterDescription.text = args.heroDetail.description
        } else {
            characterDescription.text = getString(R.string.no_description_found)
        }
    }
}