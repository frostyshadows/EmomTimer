package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException

/**
 * Class that formats the given EditText to have 2 decimal places max.
 */
class NumberTextWatcher(private val editText: EditText) : TextWatcher {

    private val format = DecimalFormat("#.##")

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        if (!s.toString().matches(Regex("\\d+."))) {
            try {
                val startLength: Int = editText.text.length
                val value: String = s.toString().replace(
                    java.lang.String.valueOf(
                        format.decimalFormatSymbols.groupingSeparator
                    ), ""
                )
                val number: Number? = format.parse(value)
                val cursorPos: Int = editText.selectionStart
                editText.setText(format.format(number))
                val endLength: Int = editText.text.length
                val selected: Int = cursorPos + (endLength - startLength)
                if (selected > 0 && selected <= editText.text.length) {
                    editText.setSelection(selected)
                } else {
                    // place cursor at the end
                    editText.setSelection(editText.text.length - 1)
                }
            } catch (nfe: NumberFormatException) {
                // No-op
            } catch (e: ParseException) {
                // No-op
            }
        }
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        // No-op
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        // No-op
    }
}