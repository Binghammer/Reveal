package com.openproject.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openproject.data.repository.RickRepository
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character), RickAdapter.ItemClickListener {
    private lateinit var rickAdapter: RickAdapter

    private lateinit var binding: FragmentCharacterBinding
    @Inject lateinit var provider: RickRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        rickAdapter = RickAdapter(requireContext())
        rickAdapter.listener = this
        binding.recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()

        provider.character(
            (1..101).toList().map { it.toString() })
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sortedByDescending { character -> character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
                rickAdapter.list = it
            }, {
                Timber.e(it, "MainActivity.onResume.okHttp.character:")
            })
    }

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