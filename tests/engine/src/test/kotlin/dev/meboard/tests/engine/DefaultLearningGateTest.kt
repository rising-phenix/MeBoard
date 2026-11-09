package dev.meboard.tests.engine

import dev.meboard.engine.api.LearningDecision
import dev.meboard.engine.learning.DefaultLearningGate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertEquals

class DefaultLearningGateTest {

    @Test
    fun `allows normal text input by default`() {
        val gate = DefaultLearningGate()
        assertEquals(LearningDecision.ALLOW, gate.decide("hello", null))
        assertEquals(LearningDecision.ALLOW, gate.decide("hello", "text"))
    }

    @Test
    fun `blocks empty tokens`() {
        val gate = DefaultLearningGate()
        assertEquals(LearningDecision.BLOCK_EMPTY, gate.decide("", null))
        assertEquals(LearningDecision.BLOCK_EMPTY, gate.decide("   ", null))
    }

    @Test
    fun `blocks password fields`() {
        val gate = DefaultLearningGate()
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("secret", "textPassword"))
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("secret", "textVisiblePassword"))
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("1234", "numberPassword"))
    }

    @Test
    fun `blocks phone and email fields`() {
        val gate = DefaultLearningGate()
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("5551234", "phone"))
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("a@b.com", "textEmailAddress"))
    }

    @Test
    fun `blocks credit card and OTP fields`() {
        val gate = DefaultLearningGate()
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("4111", "textCreditCard"))
        assertEquals(LearningDecision.BLOCK_SENSITIVE_FIELD, gate.decide("123456", "textOTP"))
    }

    @Test
    fun `blocks when paused`() {
        val gate = DefaultLearningGate()
        gate.pause()
        assertEquals(LearningDecision.BLOCK_PAUSED, gate.decide("hello", null))
        gate.resume()
        assertEquals(LearningDecision.ALLOW, gate.decide("hello", null))
    }

    @Test
    fun `isPaused reflects state`() {
        val gate = DefaultLearningGate()
        assertFalse(gate.isPaused())
        gate.pause()
        assertTrue(gate.isPaused())
        gate.resume()
        assertFalse(gate.isPaused())
    }
}
