package com.openproject.ui.figure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.openproject.data.repository.RickRepository
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentFigureDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FigureDetailFragment : Fragment() {

    private lateinit var binding: FragmentFigureDetailsBinding

    @Inject lateinit var provider: RickRepository

    private val viewModel: FigureDetailViewModel by viewModels<FigureDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFigureDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navArgs by navArgs<FigureDetailFragmentArgs>()
        binding.toolbar.title = navArgs.name
        viewModel.setArgs(navArgs.id)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.figure.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.image)
                .noFade()
                .placeholder(R.drawable.outline_person_24)
                .into(binding.image)
            binding.figure = it
        }
    }
}