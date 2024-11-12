package com.openproject.ui.character

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
class CharacterDetailViewModel @Inject constructor(
    private val rickRepository: RickRepository,
) : ViewModel() {

    private val _character: MutableLiveData<Character> = MutableLiveData()
    val character: LiveData<Character>
        get() = _character

    private lateinit var disposable: Disposable

    fun setArgs(id: Int) {
        disposable = rickRepository
            .getCharacter(id)
            .subscribe(_character::postValue, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}