package dev.meboard.app.ime

import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.InputMethodService.CustomDialog
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import dev.meboard.app.R

/**
 * MeBoard's native IME service.
 *
 * M1 milestone: minimal registration and key input. No prediction or learning yet.
 * See milestones/M1_IME_FOUNDATION.md for acceptance criteria.
 */
class MeBoardIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private var qwertyKeyboard: Keyboard? = null
    private var symbolsKeyboard: Keyboard? = null
    private var currentKeyboard: Keyboard? = null

    private var isShifted = false
    private var isCapsLocked = false
    private var isSymbols = false

    override fun onCreateInputView(): android.view.View? {
        keyboardView = layoutInflater.inflate(
            R.layout.keyboard_view,
            null,
            false
        ) as KeyboardView
        qwertyKeyboard = Keyboard(this, R.xml.qwerty)
        symbolsKeyboard = Keyboard(this, R.xml.symbols)
        currentKeyboard = qwertyKeyboard
        applyTheme()
        keyboardView.keyboard = currentKeyboard
        keyboardView.isPreviewEnabled = true
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onStartInput(
        editorInfo: EditorInfo,
        restarting: Boolean
    ) {
        super.onStartInput(editorInfo, restarting)
        // Reset shift state when input starts
        isShifted = false
        isCapsLocked = false
        updateShiftKey()
        updateKeyboard()
    }

    override fun onFinishInput() {
        super.onFinishInput()
        isShifted = false
        isCapsLocked = false
        isSymbols = false
        currentKeyboard = qwertyKeyboard
        updateKeyboard()
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        val ic: InputConnection? = currentInputConnection ?: return

        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                if (keyCodes.isNotEmpty() && keyCodes[0] == Keyboard.KEYCODE_DELETE) {
                    ic.deleteSurroundingText(1, 0)
                }
            }
            Keyboard.KEYCODE_SHIFT -> {
                toggleShift()
            }
            -1 -> { // Switch to alphabet
                isSymbols = false
                updateKeyboard()
            }
            -3 -> { // Enter/Done
                ic.performEditorAction(EditorInfo.IME_ACTION_DONE)
            }
            -4 -> { // Switch to symbols
                isSymbols = true
                updateKeyboard()
            }
            else -> {
                val c = if (isShifted || isCapsLocked) {
                    primaryCode.toChar().uppercaseChar()
                } else {
                    primaryCode.toChar()
                }
                ic.commitText(c.toString(), 1)
                // Auto-shift off after one character
                if (isShifted && !isCapsLocked) {
                    isShifted = false
                    updateShiftKey()
                }
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

    private fun toggleShift() {
        if (isCapsLocked) {
            isCapsLocked = false
            isShifted = false
        } else {
            isShifted = !isShifted
            if (isShifted) {
                // Shift toggles, not sticky (unless double-tapped, which we simplify)
                isShifted = true
            }
        }
        updateShiftKey()
    }

    private fun updateShiftKey() {
        // The KeyboardView handles shift visual state via setShifted
        keyboardView.isShifted = isShifted || isCapsLocked
    }

    private fun updateKeyboard() {
        currentKeyboard = if (isSymbols) symbolsKeyboard else qwertyKeyboard
        keyboardView.keyboard = currentKeyboard
    }

    private fun applyTheme() {
        val prefs: SharedPreferences = getSharedPreferences("meboard_prefs", Context.MODE_PRIVATE)
        val theme = prefs.getString("theme", "dark")

        val backgroundColor: Int
        val keyColor: Int
        val keyTextColor: Int
        val popupColor: Int

        when (theme) {
            "light" -> {
                backgroundColor = getColor(R.color.theme_light_background)
                keyColor = getColor(R.color.theme_light_key)
                keyTextColor = getColor(R.color.theme_light_keyText)
                popupColor = getColor(R.color.theme_light_popup)
            }
            "graphite" -> {
                backgroundColor = getColor(R.color.theme_graphite_background)
                keyColor = getColor(R.color.theme_graphite_key)
                keyTextColor = getColor(R.color.theme_graphite_keyText)
                popupColor = getColor(R.color.theme_graphite_popup)
            }
            else -> { // dark
                backgroundColor = getColor(R.color.theme_dark_background)
                keyColor = getColor(R.color.theme_dark_key)
                keyTextColor = getColor(R.color.theme_dark_keyText)
                popupColor = getColor(R.color.theme_dark_popup)
            }
        }

        keyboardView.background = android.graphics.ColorDrawable(backgroundColor)
        keyboardView.popupBackground = android.graphics.ColorDrawable(popupColor)
        // Key colors would be set per-key in a more complete theming implementation
    }

    override fun onCreateCustomDialog(): CustomDialog? {
        return null
    }

    private fun getColor(id: Int): Int {
        return resources.getColor(id, theme)
    }
}
