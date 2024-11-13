package com.openproject.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.openproject.data.model.Episode
import com.openproject.data.model.FigureWithEpisodes
import io.reactivex.rxjava3.core.Single

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodes: List<Episode>)

    @Query("SELECT * FROM Episode WHERE episodeId = :id")
    fun getEpisode(id: Int): Single<Episode>

}