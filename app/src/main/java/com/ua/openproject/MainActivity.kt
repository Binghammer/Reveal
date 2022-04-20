package com.ua.openproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ua.openproject.service.Character
import com.ua.openproject.service.ServiceProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var provider: ServiceProvider
    private lateinit var recyclerview: RecyclerView
    private lateinit var rickAdapter :RickAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("MainActivity.onCreate:")
        provider = ServiceProvider(this)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        rickAdapter = RickAdapter(this)
        recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity.onResume:")
        provider.okHttp.character((1..101).toList().map { it.toString() })
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sortedByDescending { character -> character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
                rickAdapter.list  = it
            }, {
                Timber.e(it, "MainActivity.onResume.okHttp.character:")
            })
    }
}

class RickAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = listOf<Character>()
        set(value) {
            field = value
            notifyDataSetChanged()
            Timber.d("RickAdapter.list:$list")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.d("RickAdapter.onBindViewHolder:${list[position]}")
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