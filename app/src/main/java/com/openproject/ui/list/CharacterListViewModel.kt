package com.openproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openproject.data.model.Character
import com.openproject.data.repository.RickRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(rickRepository: RickRepository) : ViewModel() {

    private val _characters: MutableLiveData<List<Character>> = MutableLiveData()
    val characters: LiveData<List<Character>>
        get() = _characters

    private val disposable: Disposable = rickRepository
        .character((1..101).toList().map { it.toString() })
        .subscribe(_characters::postValue, Timber::e)

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}