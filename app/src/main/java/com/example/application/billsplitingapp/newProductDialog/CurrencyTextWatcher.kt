package com.example.application.billsplitingapp.newProductDialog

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class CurrencyTextWatcher (val text : TextInputEditText) : TextWatcher {

    var length : Int = 0

    override fun afterTextChanged(s: Editable?) {
            text.removeTextChangedListener(this)
            var txt = text.text.toString()
            txt = txt.replace(",", "")
            if (text.length() > length) {
                txt = txt.substring(0, txt.length - 2) + "," + txt.subSequence(
                    txt.length - 2,
                    txt.length
                )
                if (txt[2] == '0') {
                    txt = txt.removeRange(2, 3)
                }            } else {
                txt = txt.substring(0, txt.length - 2) + "," + txt.subSequence(
                    txt.length - 2,
                    txt.length
                )
                if (length <= 6) {
                    txt = txt.substring(0, 2) + "0" + txt.substring(2, txt.length)
                }
            }
            text.setText(txt)
            text.setSelection(text.length())
            length = text.length()
            text.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}