package com.openproject.ui.figure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openproject.data.model.Episode
import com.openproject.data.model.Figure
import com.openproject.data.repository.FigureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FigureDetailViewModel @Inject constructor(
    private val figureRepository: FigureRepository,
) : ViewModel() {

    private val _figure: MutableLiveData<Figure> = MutableLiveData()
    val figure: LiveData<Figure>
        get() = _figure

    private val _episodes: MutableLiveData<List<Episode>> = MutableLiveData()
    val episodes: LiveData<List<Episode>>
        get() = _episodes

    private lateinit var disposable: Disposable

    fun setArgs(id: Int) {
        disposable = figureRepository
            .getFigure(id)
            .subscribe({
                _figure.postValue(it.figure)
                _episodes.postValue(it.episodes)
            }, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}