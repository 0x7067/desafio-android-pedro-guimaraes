package com.example.marveldex.ui.features.characterdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.marveldex.R
import com.example.marveldex.data.comics.MarvelComics
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_comic.view.*

class CharacterDetailsAdapter(private val comics: List<MarvelComics>): RecyclerView.Adapter<CharacterDetailsAdapter.ItemViewHolder>()  {

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comic,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val comic = comics[position]
        holder.itemView.comicTitle.text = comic.title
        holder.itemView.comicDescription.text = comic.description
        holder.itemView.comicImage.load(comic.thumbnail.getUrl())
    }
}