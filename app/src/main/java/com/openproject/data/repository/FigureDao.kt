package com.openproject.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.openproject.data.model.Figure
import com.openproject.data.model.FigureEpisodeCrossReference
import com.openproject.data.model.FigureWithEpisodes

@Dao
interface FigureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFigures(characters: List<Figure>)

    @Query("SELECT * FROM Figure")
    fun getFigures(): LiveData<List<Figure>>

    @Transaction
    @Query("SELECT * FROM Figure WHERE figureId = :id")
    suspend fun getFigureWithEpisodes(id: Int): FigureWithEpisodes

    @Insert(FigureEpisodeCrossReference::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFigureEpisodeCrossReferences(list: List<FigureEpisodeCrossReference>)
}