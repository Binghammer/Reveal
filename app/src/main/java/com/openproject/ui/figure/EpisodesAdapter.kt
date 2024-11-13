package com.openproject.ui.figure

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openproject.data.model.Episode
import com.ua.openproject.databinding.ListItemEpisodeBinding

class EpisodesAdapter(context: Context, val onEpisodeClicked: (Episode) -> Unit) :
    ListAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(diffUtil) {

    var list: List<Episode>
        get() = currentList
        set(value) = submitList(value)

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ListItemEpisodeBinding.inflate(inflater, parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Episode>() {

            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class EpisodeViewHolder(private val binding: ListItemEpisodeBinding) : ViewHolder(binding.root) {

        private lateinit var episode: Episode

        init {
            binding.body.setOnClickListener { onEpisodeClicked(episode) }
        }

        fun bind(episode: Episode) {
            this.episode = episode
            binding.episode = episode
        }

    }
}
