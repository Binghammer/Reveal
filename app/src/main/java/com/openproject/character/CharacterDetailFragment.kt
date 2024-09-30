package com.openproject.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import com.openproject.MainActivity
import com.ua.openproject.databinding.FragmentCharacterDetailsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class CharacterDetailFragment : Fragment() {

    private var _binding : FragmentCharacterDetailsBinding?= null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navArgs by navArgs<CharacterDetailFragmentArgs>()
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.toolbar.title = navArgs.name
        (activity as MainActivity)
            .provider
            .okHttp
            .character(listOf(""+navArgs.id))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Picasso.get().load(it[0].image).into(binding.image)
                binding.name.text = it[0].name
                binding.species.text = it[0].species
                binding.subSpecies.text = it[0].type
                binding.gender.text = it[0].gender
                binding.created.text = it[0].created
                binding.knownLocation.text = it[0].location.name
                binding.originName.text = it[0].origin.name
            }
    }
}