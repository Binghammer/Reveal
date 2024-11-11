package com.openproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.openproject.ui.character.CharacterHolder
import com.openproject.ui.character.RickAdapter
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character), RickAdapter.ItemClickListener {

    private lateinit var binding: FragmentCharacterBinding
    private val viewModel: CharacterListViewModel by viewModels<CharacterListViewModel>()

    private val rickAdapter: RickAdapter by lazy {
        RickAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rickAdapter.listener = this
        binding.recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner) {
            rickAdapter.list = it
        }
    }

    //TODO move this to ViewModel
    override fun onItemClick(view: RecyclerView.ViewHolder) {
        val characterHolder = view as CharacterHolder
        findNavController().navigate(
            CharacterFragmentDirections
                .characterFragmentToDetails(
                    characterHolder.character?.name!!,
                    characterHolder.character?.id!!
                )
        )
    }
}