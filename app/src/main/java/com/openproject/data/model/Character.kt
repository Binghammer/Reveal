package com.openproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Character")
data class Character(
    @PrimaryKey
    val id: Int,

    @ColumnInfo
    val name: String,
    /**
     * The status of the character ('Alive', 'Dead' or 'unknown')
     * */
    @ColumnInfo
    var status: String,

    @ColumnInfo
    var species: String,
    /**
     * The type or subspecies of the character.
     * */
    @ColumnInfo
    var type: String,

    @ColumnInfo
    var gender: String,
    /**
     * Name and link to the character's origin location.
     * */
    @ColumnInfo
    var origin: Origin,

    /**
     * Name and link to the character's last known location endpoint.
     * */
    @ColumnInfo
    val location: Location,

    @ColumnInfo
    var image: String,

    /**
     *  List of episodes in which this character appeared.
     *  */
    @ColumnInfo
    var episode: List<String>,

    /**
     * Link to the character's own URL endpoint.
     * (url)
     * */
    @ColumnInfo
    var url: String,

    @ColumnInfo
    var created: String,
)

data class Location(val name: String, val url: String)
data class Origin(val name: String, val url: String)