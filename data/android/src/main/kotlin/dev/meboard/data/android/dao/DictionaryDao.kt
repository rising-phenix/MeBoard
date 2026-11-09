package dev.meboard.data.android.dao

import androidx.room.*
import dev.meboard.data.android.model.DictionaryEntry
import java.util.concurrent.TimeUnit

/**
 * DAO for personal dictionary entries.
 * All suspending methods are safe to call from a background coroutine.
 */
@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DictionaryEntry)

    @Update
    suspend fun update(entry: DictionaryEntry)

    @Delete
    suspend fun delete(entry: DictionaryEntry)

    @Query("SELECT * FROM personal_dictionary WHERE word = :word")
    suspend fun getByWord(word: String): DictionaryEntry?

    @Query("SELECT * FROM personal_dictionary WHERE word LIKE :prefix || '%' ORDER BY count DESC, word ASC LIMIT :limit")
    suspend fun prefixMatches(prefix: String, limit: Int): List<DictionaryEntry>

    @Query("SELECT * FROM personal_dictionary ORDER BY count DESC LIMIT :limit")
    suspend fun topEntries(limit: Int): List<DictionaryEntry>

    @Query("DELETE FROM personal_dictionary")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM personal_dictionary")
    suspend fun count(): Int
}
