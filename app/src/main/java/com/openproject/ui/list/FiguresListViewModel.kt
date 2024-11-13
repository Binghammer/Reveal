package com.openproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openproject.data.model.Figure
import com.openproject.data.repository.FigureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiguresListViewModel @Inject constructor(
    figureRepository: FigureRepository,
) : ViewModel() {

    val figures: LiveData<List<Figure>> = figureRepository.getFigures()

}