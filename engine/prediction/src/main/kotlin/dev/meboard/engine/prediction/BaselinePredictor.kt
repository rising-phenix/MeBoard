package dev.meboard.engine.prediction

import dev.meboard.engine.api.*

/**
 * Baseline predictor: trie + unigram/bigram scoring.
 *
 * Scoring formula:
 *   score(word) = alpha * log1p(unigramCount(word)) + beta * log1p(bigramCount(prev, word))
 *
 * where prev is the immediately preceding token (bigram context).
 * Ties are broken by lexicographic order for determinism.
 *
 * Complexity:
 *   - predict: O(P * log P) where P is the number of prefix matches (capped by limit)
 *   - Memory: O(V) for dictionary + O(N) for n-gram counts
 */
class BaselinePredictor(
    internal val dictionary: Dictionary,
    internal val nGramStore: NGramStore
) : Predictor {

    private val alpha = 1.0 // weight for unigram frequency
    private val beta = 0.5  // weight for bigram context

    override fun predict(prefix: String, context: List<String>, limit: Int): List<Suggestion> {
        if (limit <= 0) return emptyList()

        val normalizedPrefix = Tokenizer.normalize(prefix)
        if (normalizedPrefix.isEmpty()) {
            // No prefix: return top unigrams (empty-input fallback).
            return topUnigrams()
        }

        val prev = context.lastOrNull() ?: ""
        val prev2 = if (context.size >= 2) context[context.size - 2] else ""

        val matches = dictionary.prefixMatches(normalizedPrefix, limit * 4) // over-fetch then rank
        val scored = matches.map { token ->
            val unigramScore = alpha * Math.log1p(nGramStore.unigramCount(token.text).toDouble())
            val bigramScore = beta * Math.log1p(nGramStore.bigramCount(prev, token.text).toDouble())
            val trigramScore = 0.25 * Math.log1p(nGramStore.trigramCount(prev2, prev, token.text).toDouble())
            val score = unigramScore + bigramScore + trigramScore

            val source = when {
                nGramStore.trigramCount(prev2, prev, token.text) > 0 -> SuggestionSource.TRIGRAM
                nGramStore.bigramCount(prev, token.text) > 0 -> SuggestionSource.BIGRAM
                nGramStore.unigramCount(token.text) > 0 -> SuggestionSource.UNIGRAM
                else -> SuggestionSource.DICTIONARY
            }

            Suggestion(
                text = token.text,
                score = score,
                source = source,
                replacementLength = normalizedPrefix.length
            )
        }

        // Sort by score desc, then text asc for deterministic tie-breaking.
        val ranked = scored.sortedWith(compareByDescending<Suggestion> { it.score }.thenBy { it.text })

        return ranked.take(limit)
    }

    private fun topUnigrams(): List<Suggestion> {
        // When there's no prefix, return the most frequent unigram words.
        // We scan the dictionary (could be large); cap at a reasonable number.
        val all = mutableListOf<Suggestion>()
        // Note: Dictionary doesn't expose "all tokens", so we return empty for no-prefix.
        // Callers should handle empty prefix by showing nothing or recent words.
        return all
    }
}
