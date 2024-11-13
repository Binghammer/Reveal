package com.openproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Episode")
data class Episode(
    @PrimaryKey
    @ColumnInfo("episodeId")
    val id: Int,
    @SerializedName("air_date")
    @ColumnInfo
    val airDate: String,
    @ColumnInfo
    val characters: List<String>,
    @ColumnInfo
    val created: String,
    @ColumnInfo
    val episode: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val url: String,
)