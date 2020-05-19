package com.example.application.billsplitingapp.newProductDialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CurrencyEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        start()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        start()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        start()
    }

    private fun start() {

        this.setOnClickListener {
            this.post { this.setSelection(this.length()) }
        }
        this.setOnFocusChangeListener { view: View, b: Boolean ->
            this.post { this.setSelection(this.length()) }
        }

        addTextChangedListener(object : TextWatcher {
            var length: Int = this@CurrencyEditText.length()
            var lastState: String = this@CurrencyEditText.text.toString()
            override fun afterTextChanged(s: Editable?) {
                if(this@CurrencyEditText.text.toString() != lastState) {
                    this@CurrencyEditText.removeTextChangedListener(this)
                    var txt = this@CurrencyEditText.text.toString()
                    txt = txt.replace(",", "")
                    if (this@CurrencyEditText.length() > length) {
                        if (txt[2] == '0') {
                            txt = txt.removeRange(2, 3)
                        }
                    } else if (this@CurrencyEditText.length() < length) {
                        if (length <= 6) {
                            txt = txt.substring(0, 2) + "0" + txt.substring(2, txt.length)
                        }
                    }
                    txt = txt.substring(0, txt.length - 2) + "," + txt.subSequence(
                        txt.length - 2,
                        txt.length
                    )
                    this@CurrencyEditText.setText(txt)
                    this@CurrencyEditText.setSelection(this@CurrencyEditText.length())
                    length = this@CurrencyEditText.length()
                    lastState = this@CurrencyEditText.text.toString()
                    this@CurrencyEditText.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun getFloatValue(): Float {
        var p = this.text.toString().trim()
        return if (p.isNotEmpty()) {
            p = p.removeRange(0, 2)
            p = p.replace(",", "")
            p = p.replace(".", "")
            p = p.substring(0, p.length - 2) + "." + p.substring(p.length - 2, p.length)
            p.toFloat()
        } else {
            0f
        }
    }

    fun setFloatValue(value: Float) {
        var f = value
        if (f - f.toInt() == 0f) {
            f = value.times(10f)
        }
        var final = f.toString()
        final = final.replace(".", "")
        final = "R$" + final.substring(0, final.length - 2) + "," + final.substring(
            final.length - 2,
            final.length
        )
        //price.removeTextChangedListener(textWatcher)
        this.setText(final)
        //price.addTextChangedListener(textWatcher)
    }
}