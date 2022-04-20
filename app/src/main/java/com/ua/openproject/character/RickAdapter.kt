package com.ua.openproject.character

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import com.ua.openproject.service.Character

class RickAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = listOf<Character>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as CharacterHolder
        holder.name.text = list[position].name
        holder.species.text = list[position].species
        holder.subSpecies.text = list[position].type.takeIf { it.isNotBlank() } ?: context.getString(R.string.subspecies)
        holder.known_location.text = list[position].location.name
        holder.origin_Name.text = list[position].origin.name
        Picasso.Builder(context)
            .build()
            .load(list[position].image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

data class CharacterHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var name: TextView = view.findViewById(R.id.name)
    var species: TextView = view.findViewById(R.id.species)
    var subSpecies: TextView = view.findViewById(R.id.subSpecies)
    var known_location: TextView = view.findViewById(R.id.known_location)
    var origin_Name: TextView = view.findViewById(R.id.origin_Name)
    var imageView: ImageView = view.findViewById(R.id.imageView)
}