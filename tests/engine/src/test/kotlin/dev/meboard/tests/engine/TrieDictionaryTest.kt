package dev.meboard.tests.engine

import dev.meboard.engine.dictionary.TrieDictionary
import dev.meboard.engine.api.Token
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertEquals
import org.junit.jupiter.api.assertFalse
import org.junit.jupiter.api.assertTrue

class TrieDictionaryTest {

    @Test
    fun `empty dictionary has size zero`() {
        val dict = TrieDictionary()
        assertEquals(0, dict.size())
    }

    @Test
    fun `put and get exact token`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 5))
        assertEquals(Token("hello", 5), dict.get("hello"))
        assertTrue(dict.contains("hello"))
        assertFalse(dict.contains("world"))
    }

    @Test
    fun `put updates existing token`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 1))
        dict.put(Token("hello", 10))
        assertEquals(Token("hello", 10), dict.get("hello"))
        assertEquals(1, dict.size())
    }

    @Test
    fun `prefixMatches returns words starting with prefix`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 5))
        dict.put(Token("hell", 3))
        dict.put(Token("helium", 2))
        dict.put(Token("world", 7))

        val results = dict.prefixMatches("hel", 10)
        assertEquals(3, results.size)
        assertTrue(results.all { it.text.startsWith("hel") })
    }

    @Test
    fun `prefixMatches is case insensitive`() {
        val dict = TrieDictionary()
        dict.put(Token("Hello", 5))
        val results = dict.prefixMatches("HEL", 10)
        assertEquals(1, results.size)
    }

    @Test
    fun `prefixMatches respects limit`() {
        val dict = TrieDictionary()
        dict.put(Token("aaa", 1))
        dict.put(Token("aab", 1))
        dict.put(Token("aac", 1))
        dict.put(Token("aad", 1))

        val results = dict.prefixMatches("aa", 2)
        assertEquals(2, results.size)
    }

    @Test
    fun `prefixMatches returns empty for no matches`() {
        val dict = TrieDictionary()
        dict.put(Token("hello", 1))
        assertEquals(emptyList(), dict.prefixMatches("xyz", 10))
    }

    @Test
    fun `prefixMatches results are deterministic and sorted`() {
        val dict = TrieDictionary()
        dict.put(Token("cat", 1))
        dict.put(Token("car", 1))
        dict.put(Token("cab", 1))

        val results = dict.prefixMatches("ca", 10)
        assertEquals(listOf("cab", "car", "cat"), results.map { it.text })
    }
}
