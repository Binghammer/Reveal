package com.openproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.openproject.data.model.Figure
import com.openproject.ui.list.FiguresListFragmentDirections.Companion.figureFragmentToDetails
import com.openproject.util.showDividers
import com.squareup.picasso.Picasso
import com.ua.openproject.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FiguresListFragment : Fragment() {

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
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_figures_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerview)
        recycler.adapter = rickAdapter
        recycler.showDividers()
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

    fun onFigureClicked(figure: Figure) {
        findNavController().navigate(
            figureFragmentToDetails(figure.name, figure.id)
        )
    }
}