package com.ua.openproject.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ua.openproject.MainActivity
import com.ua.openproject.R
import com.ua.openproject.databinding.FragmentCharacterBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_character.*
import timber.log.Timber

class CharcterFragment : Fragment(R.layout.fragment_character), RickAdapter.ItemClickListener {
    private lateinit var rickAdapter : RickAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentCharacterBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        rickAdapter = RickAdapter(requireContext())
        rickAdapter.listener = this
        recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).provider.okHttp.character((1..101).toList().map { it.toString() })
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sortedByDescending { character -> character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
                rickAdapter.list  = it
            }, {
                Timber.e(it, "MainActivity.onResume.okHttp.character:")
            })
    }

    override fun onItemClick(view: RecyclerView.ViewHolder) {
        val characterHolder = view as CharacterHolder
        findNavController().navigate(CharcterFragmentDirections
            .characterFragmentToDetails(characterHolder.character?.name!!, characterHolder.character?.id!!))
    }
}