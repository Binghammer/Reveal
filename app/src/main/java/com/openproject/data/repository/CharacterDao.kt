package com.openproject.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openproject.data.model.Character
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM Character WHERE id = :id")
    fun getCharacter(id: Int): Single<Character>

    @Query("SELECT * FROM Character ORDER BY name ASC LIMIT :count")
    fun getCharacters(count: Int): Observable<List<Character>>
}