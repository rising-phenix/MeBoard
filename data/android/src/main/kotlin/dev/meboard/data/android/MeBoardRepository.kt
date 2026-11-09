package dev.meboard.data.android

import android.content.Context
import androidx.room.WithKt
import dev.meboard.data.android.dao.DictionaryDao
import dev.meboard.data.android.dao.NGramDao
import dev.meboard.data.android.dao.SettingsDao
import dev.meboard.data.android.db.AppDatabase
import dev.meboard.data.android.model.DictionaryEntry
import dev.meboard.data.android.model.NGramCount
import dev.meboard.engine.api.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository boundary between the engine (pure Kotlin) and Android persistence.
 *
 * All database operations are offloaded to a background dispatcher.
 * Implements both [Dictionary] and [NGramStore] so the engine can use them directly.
 */
class MeBoardRepository(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Dictionary, NGramStore {

    private val db: AppDatabase = AppDatabase.getDatabase(context)

    // ---------- Dictionary ----------

    override fun prefixMatches(prefix: String, limit: Int): List<Token> {
        return withContext(dispatcher) {
            WithKt.runBlocking {
                val entries = db.dictionaryDao().prefixMatches(prefix, limit)
                entries.map { Token(it.word, it.count, it.lastSeenMillis) }
            }
        }
    }

    override fun get(text: String): Token? {
        return withContext(dispatcher) {
            WithKt.runBlocking {
                val entry = db.dictionaryDao().getByWord(text)
                entry?.let { Token(it.word, it.count, it.lastSeenMillis) }
            }
        }
    }

    override fun put(token: Token) {
        withContext(dispatcher) {
            WithKt.runBlocking {
                db.dictionaryDao().insert(
                    DictionaryEntry(token.text, token.count, token.lastSeenMillis)
                )
            }
        }
    }

    override fun size(): Int {
        return withContext(dispatcher) {
            WithKt.runBlocking { db.dictionaryDao().count() }
        }
    }

    override fun contains(text: String): Boolean {
        return withContext(dispatcher) {
            WithKt.runBlocking { db.dictionaryDao().getByWord(text) != null }
        }
    }

    // ---------- NGramStore ----------

    override fun unigramCount(word: String): Long {
        return withContext(dispatcher) {
            WithKt.runBlocking {
                db.nGramDao().getByKey(word)?.count ?: 0L
            }
        }
    }

    override fun bigramCount(prev: String, word: String): Long {
        return withContext(dispatcher) {
            WithKt.runBlocking {
                val key = "$prev\u0000$word"
                db.nGramDao().getByKey(key)?.count ?: 0L
            }
        }
    }

    override fun trigramCount(prev2: String, prev1: String, word: String): Long {
        return withContext(dispatcher) {
            WithKt.runBlocking {
                val key = "$prev2\u0000$prev1\u0000$word"
                db.nGramDao().getByKey(key)?.count ?: 0L
            }
        }
    }

    override fun incrementUnigram(word: String) {
        increment(word, 1)
    }

    override fun incrementBigram(prev: String, word: String) {
        increment("$prev\u0000$word", 2)
    }

    override fun incrementTrigram(prev2: String, prev1: String, word: String) {
        increment("$prev2\u0000$prev1\u0000$word", 3)
    }

    private fun increment(key: String, order: Int) {
        withContext(dispatcher) {
            WithKt.runBlocking {
                val existing = db.nGramDao().getByKey(key)
                val count = if (existing != null) existing.count + 1 else 1L
                db.nGramDao().upsert(NGramCount(key, count, order))
            }
        }
    }

    // ---------- Settings ----------

    suspend fun getSetting(key: String, defaultValue: String = ""): String {
        return db.settingsDao().getByKey(key)?.value ?: defaultValue
    }

    suspend fun putSetting(key: String, value: String) {
        db.settingsDao().insert(AppSetting(key, value))
    }

    /** Clears all learned data (dictionary, n-grams). Settings are preserved. */
    suspend fun clearLearnedData() {
        db.dictionaryDao().clearAll()
        db.nGramDao().clearAll()
    }

    /** Whether learning is globally paused. Stored in settings. */
    suspend fun isLearningPaused(): Boolean {
        return db.settingsDao().getByKey("learning_paused")?.value == "true"
    }

    suspend fun setLearningPaused(paused: Boolean) {
        db.settingsDao().insert(AppSetting("learning_paused", if (paused) "true" else "false"))
    }
}
