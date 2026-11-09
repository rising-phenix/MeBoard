package dev.meboard.data.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A personal dictionary entry learned by the keyboard.
 *
 * Only normalized words are stored; no raw sentences or keystroke logs.
 */
@Entity(tableName = "personal_dictionary")
data class DictionaryEntry(
    @PrimaryKey val word: String,
    val count: Long = 0,
    val lastSeenMillis: Long = 0
)
