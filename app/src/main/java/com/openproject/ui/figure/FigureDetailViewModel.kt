package com.openproject.ui.figure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openproject.data.model.Figure
import com.openproject.data.repository.RickRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FigureDetailViewModel @Inject constructor(
    private val rickRepository: RickRepository,
) : ViewModel() {

    private val _figure: MutableLiveData<Figure> = MutableLiveData()
    val figure: LiveData<Figure>
        get() = _figure

    private lateinit var disposable: Disposable

    fun setArgs(id: Int) {
        disposable = rickRepository
            .getFigure(id)
            .subscribe(_figure::postValue, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}