package com.openproject.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.openproject.data.repository.RickRepository
import com.squareup.picasso.Picasso
import com.ua.openproject.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    @Inject lateinit var provider: RickRepository

    private val viewModel: CharacterDetailViewModel by viewModels<CharacterDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navArgs by navArgs<CharacterDetailFragmentArgs>()
        binding.toolbar.title = navArgs.name
        viewModel.setArgs(navArgs.id)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) {
            Picasso.get().load(it.image).into(binding.image)
            binding.character = it
        }
    }
}