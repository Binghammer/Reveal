package com.openproject.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.openproject.data.model.Figure
import com.openproject.data.model.FigureEpisodeCrossReference
import com.openproject.data.model.FigureWithEpisodes
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FigureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFigures(characters: List<Figure>)

    @Query("SELECT * FROM Figure WHERE figureId = :id")
    fun getFigure(id: Int): Single<Figure>

    @Query("SELECT * FROM Figure")
    fun getFigures(): Observable<List<Figure>>

    @Transaction
    @Query("SELECT * FROM Figure WHERE figureId = :id")
    fun getFigureWithEpisodes(id: Int): Single<FigureWithEpisodes>

    @Insert(FigureEpisodeCrossReference::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertFigureEpisodeCrossReferences(list: List<FigureEpisodeCrossReference>)
}