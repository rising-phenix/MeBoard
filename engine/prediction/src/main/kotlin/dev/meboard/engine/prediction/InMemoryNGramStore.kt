package dev.meboard.engine.prediction

import dev.meboard.engine.api.*

/**
 * NGramStore implementation with thread-safe concurrent maps.
 *
 * Stores unigram, bigram, and trigram counts in memory.
 * All operations are O(1) average.
 */
class InMemoryNGramStore : NGramStore {

    private val unigramCounts = java.util.concurrent.ConcurrentHashMap<String, Long>()
    private val bigramCounts = java.util.concurrent.ConcurrentHashMap<String, Long>()
    private val trigramCounts = java.util.concurrent.ConcurrentHashMap<String, Long>()

    private fun bigramKey(prev: String, word: String) = "$prev\u0000$word"
    private fun trigramKey(prev2: String, prev1: String, word: String) = "$prev2\u0000$prev1\u0000$word"

    override fun unigramCount(word: String): Long =
        unigramCounts[word] ?: 0L

    override fun bigramCount(prev: String, word: String): Long =
        bigramCounts[bigramKey(prev, word)] ?: 0L

    override fun trigramCount(prev2: String, prev1: String, word: String): Long =
        trigramCounts[trigramKey(prev2, prev1, word)] ?: 0L

    override fun incrementUnigram(word: String) {
        unigramCounts.merge(word, 1L) { old, _ -> old + 1 }
    }

    override fun incrementBigram(prev: String, word: String) {
        bigramCounts.merge(bigramKey(prev, word), 1L) { old, _ -> old + 1 }
    }

    override fun incrementTrigram(prev2: String, prev1: String, word: String) {
        trigramCounts.merge(trigramKey(prev2, prev1, word), 1L) { old, _ -> old + 1 }
    }
}
