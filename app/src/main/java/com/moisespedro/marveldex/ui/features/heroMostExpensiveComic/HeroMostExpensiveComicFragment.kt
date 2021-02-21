package com.moisespedro.marveldex.ui.features.heroMostExpensiveComic

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.moisespedro.marveldex.R
import com.moisespedro.marveldex.data.comics.MarvelComics
import com.moisespedro.marveldex.data.network.Status
import kotlinx.android.synthetic.main.item_comic.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class HeroMostExpensiveComicFragment : Fragment(R.layout.hero_most_expensive_comic_fragment),
    CoroutineScope by MainScope() {

    private val viewModel: HeroMostExpensiveComicViewModel by viewModel()
    private val args: HeroMostExpensiveComicFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        launch {
            val comicsResource = viewModel.fetchHeroComics(args.heroId)

            when (comicsResource.status) {
                Status.SUCCESS -> {
                    val comicIndex =
                        getMostExpensiveComicIndex(comicsResource.data!!.heroData.results)
                    renderComic(comicsResource.data.heroData.results[comicIndex])
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), comicsResource.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun renderComic(comic: MarvelComics) {
        comicTitle.text = comic.title
        comicDescription.text = comic.description
        comicImage.load(comic.thumbnail.getUrl())
        comicValue.text =
            NumberFormat.getCurrencyInstance(Locale.US).format(comic.getComicMostExpensivePrice())
    }

    private fun getMostExpensiveComicIndex(results: List<MarvelComics>): Int {
        var mostExpensiveComicIndex = 0
        var mostExpensivePrice = 0.0
        results.forEachIndexed { index, marvelComics ->
            val comicPrice = marvelComics.getComicMostExpensivePrice()
            if (comicPrice > mostExpensivePrice) {
                mostExpensiveComicIndex = index
                mostExpensivePrice = comicPrice
            }
        }
        return mostExpensiveComicIndex
    }
}