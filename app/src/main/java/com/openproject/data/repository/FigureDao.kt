package com.openproject.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openproject.data.model.Figure
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FigureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFigures(characters: List<Figure>)

    @Query("SELECT * FROM Figure WHERE id = :id")
    fun getFigure(id: Int): Single<Figure>

    @Query("SELECT * FROM Figure ORDER BY name ASC LIMIT :count")
    fun getFigures(count: Int): Observable<List<Figure>>
}