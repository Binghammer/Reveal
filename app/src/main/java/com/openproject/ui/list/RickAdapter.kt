package com.openproject.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openproject.data.model.Figure
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import com.ua.openproject.databinding.ListItemCharacterBinding

class RickAdapter(
    private val context: Context,
    private val picasso: Picasso,
    private val onFigureClicked: (Figure) -> Unit,
) : ListAdapter<Figure, RecyclerView.ViewHolder>(diffUtil) {

    var list: List<Figure>
        get() = currentList
        set(value) = submitList(value)

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(ListItemCharacterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as CharacterHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Figure>() {

            override fun areItemsTheSame(oldItem: Figure, newItem: Figure): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Figure, newItem: Figure): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CharacterHolder(
        private val binding: ListItemCharacterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var figure: Figure

        init {
            binding.root.setOnClickListener { onFigureClicked(figure) }
        }

        fun bind(figure: Figure) {
            this.figure = figure
            binding.figure = figure
            picasso
                .load(figure.image)
                .noFade()
                .placeholder(R.drawable.outline_person_24)
                .resizeDimen(R.dimen.small_image_height, R.dimen.small_image_width)
                .centerInside()
                .into(binding.figureImage)
        }
    }

}
