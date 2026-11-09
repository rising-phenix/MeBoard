package dev.meboard.data.android.dao

import androidx.room.*
import dev.meboard.data.android.model.AppSetting

/**
 * DAO for app settings.
 */
@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: AppSetting)

    @Query("SELECT * FROM settings WHERE key = :key")
    suspend fun getByKey(key: String): AppSetting?

    @Query("SELECT * FROM settings")
    suspend fun getAll(): List<AppSetting>

    @Query("DELETE FROM settings")
    suspend fun clearAll()
}
