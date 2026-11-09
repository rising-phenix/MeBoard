package dev.meboard.data.android.dao

import androidx.room.*
import dev.meboard.data.android.model.NGramCount
import java.util.concurrent.TimeUnit

/**
 * DAO for n-gram aggregate counts.
 */
@Dao
interface NGramDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(count: NGramCount)

    @Upsert
    suspend fun upsert(count: NGramCount)

    @Query("SELECT * FROM ngram_counts WHERE ngramKey = :key")
    suspend fun getByKey(key: String): NGramCount?

    @Query("SELECT * FROM ngram_counts WHERE order = :order ORDER BY count DESC LIMIT :limit")
    suspend fun topByOrder(order: Int, limit: Int): List<NGramCount>

    @Query("DELETE FROM ngram_counts")
    suspend fun clearAll()

    @Query("SELECT SUM(count) FROM ngram_counts WHERE order = :order")
    suspend fun totalCount(order: Int): Long?
}
