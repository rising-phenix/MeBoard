package dev.meboard.engine.learning

import dev.meboard.engine.api.LearningDecision
import dev.meboard.engine.api.LearningGate

/**
 * Conservative [LearningGate] implementation.
 *
 * Learning is blocked for:
 * - Empty or blank tokens
 * - Password / sensitive input fields (detected via editorType hints)
 * - When learning is globally paused
 *
 * Editor type hints are conservative: unknown types default to ALLOW.
 */
class DefaultLearningGate : LearningGate {

    private var paused: Boolean = false

    // Editor input type hints that indicate sensitive content.
    // These mirror Android's InputType class constants where applicable.
    private val sensitiveTypes = setOf(
        "textPassword",
        "textVisiblePassword",
        "textWebPassword",
        "numberPassword",
        "number",
        "numberSigned",
        "numberDecimal",
        "phone",
        "datetime",
        "date",
        "time",
        "textUri",
        "textEmailAddress",
        "textPersonName",
        "textPostalAddress",
        "textCreditCard",
        "textOTP",
        "textNoSuggestions"
    )

    override fun decide(token: String, editorType: String?): LearningDecision {
        if (token.isBlank()) return LearningDecision.BLOCK_EMPTY
        if (paused) return LearningDecision.BLOCK_PAUSED
        if (editorType != null && editorType in sensitiveTypes) {
            return LearningDecision.BLOCK_SENSITIVE_FIELD
        }
        return LearningDecision.ALLOW
    }

    /** Pauses all learning writes. */
    fun pause() { paused = true }

    /** Resumes learning writes. */
    fun resume() { paused = false }

    /** Whether learning is currently paused. */
    fun isPaused(): Boolean = paused
}
