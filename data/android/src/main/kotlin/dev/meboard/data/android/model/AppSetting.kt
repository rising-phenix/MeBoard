package dev.meboard.data.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User settings persisted locally.
 */
@Entity(tableName = "settings", primaryKeys = ["key"])
data class AppSetting(
    val key: String,
    val value: String
)
