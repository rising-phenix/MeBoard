package dev.meboard.tests.engine

import dev.meboard.engine.api.*
import dev.meboard.engine.dictionary.TrieDictionary
import dev.meboard.engine.prediction.BaselinePredictor
import dev.meboard.engine.prediction.InMemoryNGramStore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertEquals
import org.junit.jupiter.api.assertTrue

class BaselinePredictorTest {

    @Test
    fun `predict with empty prefix and no context returns empty`() {
        val dict = TrieDictionary()
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("", emptyList(), 5)
        assertEquals(emptyList(), suggestions)
    }

    @Test
    fun `predict returns dictionary words matching prefix`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 10))
        dict.put(Token("hell", 5))
        dict.put(Token("helium", 3))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("hel", emptyList(), 10)
        assertEquals(3, suggestions.size)
        assertTrue(suggestions.all { it.text.startsWith("hel") })
    }

    @Test
    fun `predict ranks by score descending`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 100))
        dict.put(Token("hell", 1))
        val store = InMemoryNGramStore()
        store.incrementUnigram("hello")
        store.incrementUnigram("hello")
        store.incrementUnigram("hello")
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("hel", emptyList(), 10)
        assertEquals(2, suggestions.size)
        assertEquals("hello", suggestions[0].text)
    }

    @Test
    fun `predict uses bigram context`() {
        val dict = TrieDictionary()
        dict.put(Token("world", 1))
        dict.put(Token("there", 1))
        val store = InMemoryNGramStore()
        store.incrementBigram("hello", "world")
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("w", listOf("hello"), 10)
        assertTrue(suggestions.any { it.text == "world" })
    }

    @Test
    fun `predict respects limit`() {
        val dict = TrieDictionary()
        dict.put(Token("aaa", 1))
        dict.put(Token("aab", 1))
        dict.put(Token("aac", 1))
        dict.put(Token("aad", 1))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("aa", emptyList(), 2)
        assertEquals(2, suggestions.size)
    }

    @Test
    fun `suggestion replacementLength equals prefix length`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 1))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        val suggestions = predictor.predict("hel", emptyList(), 10)
        assertEquals(3, suggestions[0].replacementLength)
    }

    @Test
    fun `predict is deterministic for same input`() {
        val dict = TrieDictionary()
        dict.put(Token("test", 5))
        dict.put(Token("testing", 3))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        val run1 = predictor.predict("tes", emptyList(), 10)
        val run2 = predictor.predict("tes", emptyList(), 10)
        assertEquals(run1, run2)
    }

    @Test
    fun `predict with limit zero returns empty`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 1))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        assertEquals(emptyList(), predictor.predict("h", emptyList(), 0))
    }

    @Test
    fun `predict with negative limit returns empty`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 1))
        val store = InMemoryNGramStore()
        val predictor = BaselinePredictor(dict, store)

        assertEquals(emptyList(), predictor.predict("h", emptyList(), -1))
    }
}
