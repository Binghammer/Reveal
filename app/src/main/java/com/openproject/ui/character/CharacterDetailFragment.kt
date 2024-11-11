package com.openproject.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.openproject.data.repository.RickRepository
import com.squareup.picasso.Picasso
import com.ua.openproject.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    @Inject lateinit var provider: RickRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navArgs by navArgs<CharacterDetailFragmentArgs>()
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.toolbar.title = navArgs.name

        // TODO move to VM
        // TODO Use Coroutines
        provider
            .character(listOf("" + navArgs.id))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
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