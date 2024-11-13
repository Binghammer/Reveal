package com.openproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openproject.data.model.Figure
import com.openproject.data.repository.EpisodeRepository
import com.openproject.data.repository.FigureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FiguresListViewModel @Inject constructor(
    figureRepository: FigureRepository,
    episodeRepository: EpisodeRepository) : ViewModel() {

        init {
            episodeRepository
        }

    private val _figures: MutableLiveData<List<Figure>> = MutableLiveData()
    val figures: LiveData<List<Figure>>
        get() = _figures

    private val disposable: Disposable = figureRepository
        .getFigures()
        .subscribe(_figures::postValue, Timber::e)

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}