package dev.meboard.app.ime

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.InputMethodService.CustomDialog
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

/**
 * MeBoard's native IME service.
 *
 * M1 milestone: minimal registration and key input. No prediction or learning yet.
 * See milestones/M1_IME_FOUNDATION.md for acceptance criteria.
 */
class MeBoardIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard

    override fun onCreateInputView(): android.view.View? {
        keyboardView = layoutInflater.inflate(
            R.layout.keyboard_view,
            null,
            false
        ) as KeyboardView
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView.keyboard = keyboard
        keyboardView.isPreviewEnabled = true
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onStartInput(
        editorInfo: EditorInfo,
        restarting: Boolean
    ) {
        super.onStartInput(editorInfo, restarting)
        // Conservative default: do not learn or suggest until privacy gates are in place (M3).
        closeShiftedState()
    }

    override fun onFinishInput() {
        super.onFinishInput()
        closeShiftedState()
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        val ic: InputConnection? = currentInputConnection ?: return
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
            Keyboard.KEYCODE_SHIFT -> {
                // Toggle shift is handled by the keyboard view itself.
            }
            Keyboard.KEYCODE_DONE -> ic.performEditorAction(EditorInfo.IME_ACTION_DONE)
            else -> {
                val c = primaryCode.toChar()
                ic.commitText(c.toString(), 1)
            }
        }
    }

    override fun onPress(primaryCode: Int) {}

    override fun onRelease(primaryCode: Int) {}

    override fun onText(text: CharSequence) {
        val ic: InputConnection? = currentInputConnection ?: return
        ic.commitText(text, 1)
    }

    override fun swipeDown() {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeUp() {}

    private fun closeShiftedState() {
        // Shift state is managed by the KeyboardView; no persistent state needed here.
    }

    override fun onCreateCustomDialog(): CustomDialog? {
        return null
    }
}
