package dev.meboard.engine.prediction

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertEquals
import org.junit.jupiter.api.assertTrue

class TokenizerTest {

    @Test
    fun `tokenizes simple sentence into lowercase tokens`() {
        val tokens = Tokenizer.tokenize("Hello world")
        assertEquals(listOf("hello", "world"), tokens)
    }

    @Test
    fun `splits on punctuation and whitespace`() {
        val tokens = Tokenizer.tokenize("Hello, world! How are you?")
        assertEquals(listOf("hello", "world", "how", "are", "you"), tokens)
    }

    @Test
    fun `empty string returns empty list`() {
        assertEquals(emptyList(), Tokenizer.tokenize(""))
    }

    @Test
    fun `only delimiters returns empty list`() {
        assertEquals(emptyList(), Tokenizer.tokenize("   ,!  . "))
    }

    @Test
    fun `strips surrounding punctuation from tokens`() {
        val tokens = Tokenizer.tokenize("\"quoted\" (parenthesized) [bracketed]")
        assertEquals(listOf("quoted", "parenthesized", "bracketed"), tokens)
    }

    @Test
    fun `normalizes mixed case`() {
        assertEquals("hello", Tokenizer.normalize("HELLO"))
        assertEquals("hello", Tokenizer.normalize("HeLlO"))
    }

    @Test
    fun `lastN returns all tokens when fewer than n`() {
        assertEquals(listOf("a", "b"), Tokenizer.lastN(listOf("a", "b"), 5))
    }

    @Test
    fun `lastN returns last n tokens`() {
        assertEquals(listOf("c", "d"), Tokenizer.lastN(listOf("a", "b", "c", "d"), 2))
    }

    @Test
    fun `lastN handles empty list`() {
        assertEquals(emptyList(), Tokenizer.lastN(emptyList(), 3))
    }
}
