package dev.meboard.data.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Aggregate n-gram count for prediction.
 *
 * Stores only counts, never the raw text context beyond the n-gram itself.
 */
@Entity(
    tableName = "ngram_counts",
    primaryKeys = ["ngramKey"]
)
data class NGramCount(
    val ngramKey: String,
    val count: Long = 0,
    val order: Int = 1 // 1=unigram, 2=bigram, 3=trigram
)
