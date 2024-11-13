package com.openproject.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["figureId", "episodeId"])
data class FigureEpisodeCrossReference(
    val figureId: Int,
    val episodeId: Int,
)

data class FigureWithEpisodes(
    @Embedded val figure: Figure,
    @Relation(
        parentColumn = "figureId",
        entityColumn = "episodeId",
        associateBy = Junction(FigureEpisodeCrossReference::class)
    )
    val episodes: List<Episode>,
)