package com.openproject.ui.figure

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.openproject.data.model.Episode
import com.openproject.data.repository.FigureRepository
import com.openproject.ui.MainActivity
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentFigureDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FigureDetailFragment : Fragment() {

    private lateinit var binding: FragmentFigureDetailsBinding

    private val episodesAdapter: EpisodesAdapter by lazy {
        EpisodesAdapter(requireContext(), ::onEpisodeClicked)
    }

    @Inject lateinit var provider: FigureRepository
    @Inject lateinit var picasso: Picasso

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
        (requireActivity() as MainActivity).toolbar.title = navArgs.name
        viewModel.setArgs(navArgs.id)
        setupViews()
        observeViewModel()
    }

    private fun onEpisodeClicked(episode: Episode) {
        //TODO
        Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
    }

    private fun setupViews() {
        binding.figureEpisodeRecycler.adapter = episodesAdapter
    }

    private fun observeViewModel() {
        val margin = requireContext().resources.getDimension(R.dimen.margin_small) * 2
        val width = Resources.getSystem().displayMetrics.widthPixels - margin

        val unknownString = requireContext().getString(R.string.unknown)

        viewModel.figure.observe(viewLifecycleOwner) {
            val figure = it.copy(
                species = it.species.ifBlank { unknownString },
                type = it.type.ifBlank { unknownString },
                created = it.created.ifBlank { unknownString },
                origin = it.origin.copy(name = it.origin.name.ifBlank { unknownString }),
                location = it.location.copy(name = it.location.name.ifBlank { unknownString })
            )
            picasso.load(it.image)
                .noFade()
                .resize(width.toInt(), 0)
                .centerInside()
                .into(binding.figureImage)
            binding.figure = figure
        }

        viewModel.episodes.observe(viewLifecycleOwner) {
            episodesAdapter.list = it
        }
    }
}