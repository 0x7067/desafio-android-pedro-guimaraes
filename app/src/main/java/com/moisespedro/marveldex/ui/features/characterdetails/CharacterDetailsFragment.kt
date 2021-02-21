package com.moisespedro.marveldex.ui.features.characterdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.moisespedro.marveldex.R
import com.moisespedro.marveldex.databinding.CharacterDetailsFragmentBinding
import com.moisespedro.marveldex.extensions.viewBinding

class CharacterDetailsFragment : Fragment(R.layout.character_details_fragment) {

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val binding by viewBinding(CharacterDetailsFragmentBinding::bind)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        renderCharacter()
    }

    private fun renderCharacter() {
        binding.itemCharacter.characterName.text = args.heroDetail.name
        binding.itemCharacter.characterImage.load(args.heroDetail.imageUrl)
        renderCharacterDescription()
        binding.buttonComic.setOnClickListener {
            val directions =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToHeroMostExpensiveComicFragment(
                    args.heroDetail.id
                )
            findNavController().navigate(directions)
        }
    }

    private fun renderCharacterDescription() {
        binding.itemCharacter.characterDescription.visibility = View.VISIBLE
        if (args.heroDetail.description.isNotEmpty()) {
            binding.itemCharacter.characterDescription.text = args.heroDetail.description
        } else {
            binding.itemCharacter.characterDescription.text = getString(R.string.no_description_found)
        }
    }
}