package dev.meboard.tests.engine

import dev.meboard.engine.prediction.InMemoryNGramStore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertEquals

class InMemoryNGramStoreTest {

    @Test
    fun `new store has zero counts`() {
        val store = InMemoryNGramStore()
        assertEquals(0L, store.unigramCount("hello"))
        assertEquals(0L, store.bigramCount("hello", "world"))
        assertEquals(0L, store.trigramCount("the", "quick", "brown"))
    }

    @Test
    fun `incrementUnigram increases count`() {
        val store = InMemoryNGramStore()
        store.incrementUnigram("hello")
        assertEquals(1L, store.unigramCount("hello"))
        store.incrementUnigram("hello")
        assertEquals(2L, store.unigramCount("hello"))
    }

    @Test
    fun `incrementBigram tracks pairs`() {
        val store = InMemoryNGramStore()
        store.incrementBigram("hello", "world")
        assertEquals(1L, store.bigramCount("hello", "world"))
        assertEquals(0L, store.bigramCount("world", "hello"))
        assertEquals(0L, store.bigramCount("hello", "there"))
    }

    @Test
    fun `incrementTrigram tracks triples`() {
        val store = InMemoryNGramStore()
        store.incrementTrigram("the", "quick", "brown")
        assertEquals(1L, store.trigramCount("the", "quick", "brown"))
        assertEquals(0L, store.trigramCount("quick", "brown", "fox"))
    }

    @Test
    fun `counts are independent per n-gram order`() {
        val store = InMemoryNGramStore()
        store.incrementUnigram("hello")
        store.incrementBigram("hello", "world")
        store.incrementTrigram("say", "hello", "world")

        assertEquals(1L, store.unigramCount("hello"))
        assertEquals(1L, store.bigramCount("hello", "world"))
        assertEquals(1L, store.trigramCount("say", "hello", "world"))
        // Unigram of "world" is still 0
        assertEquals(0L, store.unigramCount("world"))
    }
}
