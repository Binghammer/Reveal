package com.openproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.openproject.data.model.Figure
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentFiguresListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FiguresListFragment : Fragment(R.layout.fragment_figures_list) {

    private lateinit var binding: FragmentFiguresListBinding
    private val viewModel: FiguresListViewModel by viewModels<FiguresListViewModel>()

    @Inject lateinit var picasso: Picasso

    private val rickAdapter: RickAdapter by lazy {
        RickAdapter(requireContext(), picasso, ::onFigureClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFiguresListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.figures.observe(viewLifecycleOwner) {
            rickAdapter.list = it
        }
    }

    //TODO normally, I would move this to the ViewModel
    fun onFigureClicked(figure: Figure) {
        findNavController().navigate(
            FiguresListFragmentDirections
                .figureFragmentToDetails(
                    figure.name,
                    figure.id
                )
        )
    }
}