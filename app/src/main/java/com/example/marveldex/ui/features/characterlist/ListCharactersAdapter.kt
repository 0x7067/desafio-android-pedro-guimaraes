package com.example.marveldex.ui.features.characterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.marveldex.R
import com.example.marveldex.data.heroes.MarvelHero
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_character.view.*
import kotlin.reflect.KFunction3

class ListCharactersAdapter(private val heroList: MutableList<MarvelHero>, private val itemClick: KFunction3<@ParameterName(
    name = "heroId"
) Int, @ParameterName(name = "heroName") String, @ParameterName(name = "heroImageURL") String, Unit>
) : RecyclerView.Adapter<ListCharactersAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_character,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val hero = heroList[position]
        holder.itemView.characterName.text = hero.name
        val heroUrl = hero.thumbnail.path.replace("http", "https") + "." + hero.thumbnail.extension

        holder.itemView.characterImage.load(heroUrl)

        holder.itemView.setOnClickListener {
            itemClick(hero.id, hero.name, heroUrl)
        }
    }

    fun setHeroesList(heroList: List<MarvelHero>) {
        this.heroList.addAll(heroList)
        notifyDataSetChanged()
    }

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun getItemCount(): Int {
        return heroList.size
    }
}