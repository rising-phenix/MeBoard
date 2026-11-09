package dev.meboard.engine.prediction

import dev.meboard.engine.api.*

/**
 * Tokenizer and normalizer for MeBoard.
 *
 * Splits text into tokens on whitespace and punctuation boundaries, lowercases,
 * and strips surrounding punctuation. Deterministic and dependency-free.
 */
object Tokenizer {

    /** Characters that delimit tokens. */
    private val delimiters = setOf(
        ' ', '\t', '\n', '\r', ',', '.', '!', '?', ';', ':',
        '"', '\'', '(', ')', '[', ']', '{', '}', '<', '>',
        '/', '\\', '|', '@', '#', '$', '%', '^', '&', '*',
        '+', '=', '~', '`', '–', '—', '…'
    )

    /**
     * Tokenizes [text] into a list of normalized tokens.
     * Empty tokens are discarded.
     */
    fun tokenize(text: String): List<String> {
        val tokens = mutableListOf<String>()
        val current = StringBuilder()
        for (ch in text) {
            if (ch in delimiters) {
                val tok = current.toString().trim()
                if (tok.isNotEmpty()) tokens.add(normalize(tok))
                current.setLength(0)
            } else {
                current.append(ch)
            }
        }
        val tok = current.toString().trim()
        if (tok.isNotEmpty()) tokens.add(normalize(tok))
        return tokens
    }

    /** Normalizes a single token: lowercase and strip surrounding non-letter characters. */
    fun normalize(token: String): String {
        var s = token.lowercase()
        // Strip leading non-letters
        var start = 0
        while (start < s.length && !s[start].isLetter() && !s[start].isDigit()) start++
        // Strip trailing non-letters
        var end = s.length - 1
        while (end >= start && !s[end].isLetter() && !s[end].isDigit()) end--
        return if (start > end) "" else s.substring(start, end + 1)
    }

    /** Returns the last N tokens from [tokens]. */
    fun lastN(tokens: List<String>, n: Int): List<String> {
        return if (tokens.size <= n) tokens else tokens.subList(tokens.size - n, tokens.size)
    }
}
