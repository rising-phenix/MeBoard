package dev.meboard.engine.api

/**
 * A single normalized token/word in the MeBoard dictionary.
 */
data class Token(
    /** The normalized text of the token (lowercased, accents stripped where applicable). */
    val text: String,
    /** Unigram frequency count (how often this word has been seen). */
    val count: Long = 0,
    /** Last-seen timestamp in millis for recency scoring. */
    val lastSeenMillis: Long = 0
)

/**
 * A single suggestion returned by the predictor.
 */
data class Suggestion(
    /** The suggested text to insert/complete. */
    val text: String,
    /** Score used for ranking (higher = better). */
    val score: Double,
    /** The source of this suggestion: unigram, bigram, trigram, dictionary, etc. */
    val source: SuggestionSource,
    /** How many characters of the user's current prefix this suggestion replaces. */
    val replacementLength: Int = 0
)

/** The provenance of a suggestion, used for debugging and explainability. */
enum class SuggestionSource {
    DICTIONARY, UNIGRAM, BIGRAM, TRIGRAM, CORRECTION
}

/**
 * Interface for the personal dictionary and prefix index.
 * Implementations must be thread-safe.
 */
interface Dictionary {
    /** Returns all dictionary entries whose text starts with [prefix], up to [limit]. */
    fun prefixMatches(prefix: String, limit: Int): List<Token>

    /** Returns the exact [Token] for [text], or null if absent. */
    fun get(text: String): Token?

    /** Adds or updates a token in the dictionary. */
    fun put(token: Token)

    /** Returns the total number of distinct tokens in the dictionary. */
    fun size(): Int

    /** Checks whether [text] exists in the dictionary. */
    fun contains(text: String): Boolean
}

/**
 * Interface for n-gram count storage (unigram, bigram, trigram).
 */
interface NGramStore {
    /** Returns the count of [word]. */
    fun unigramCount(word: String): Long

    /** Returns the count of the bigram [prev] -> [word]. */
    fun bigramCount(prev: String, word: String): Long

    /** Returns the count of the trigram [prev2] [prev1] -> [word]. */
    fun trigramCount(prev2: String, prev1: String, word: String): Long

    /** Increments the unigram count of [word]. */
    fun incrementUnigram(word: String)

    /** Increments the bigram count of [prev] -> [word]. */
    fun incrementBigram(prev: String, word: String)

    /** Increments the trigram count of [prev2] [prev1] -> [word]. */
    fun incrementTrigram(prev2: String, prev1: String, word: String)
}

/**
 * Interface for generating next-word suggestions from context.
 * Implementations must be deterministic for identical state and context.
 */
interface Predictor {
    /**
     * Returns up to [limit] suggestions for completing [prefix] given the preceding [context] tokens.
     *
     * @param prefix the current (possibly partial) word being typed
     * @param context the previous tokens, most recent last (may be empty)
     * @param limit maximum number of suggestions to return
     */
    fun predict(prefix: String, context: List<String>, limit: Int): List<Suggestion>
}

/** Result of a learning/prediction pipeline update. */
data class LearningResult(
    val accepted: Boolean,
    val token: String,
    val reason: String? = null
)

/** Decision made by the privacy/learning gate. */
enum class LearningDecision {
    ALLOW, BLOCK_SENSITIVE_FIELD, BLOCK_PAUSED, BLOCK_EMPTY
}

/** Privacy gate: decides whether a given token may be learned. */
interface LearningGate {
    /** Decides whether [token] entered in [editorType] may be learned. */
    fun decide(token: String, editorType: String?): LearningDecision
}
