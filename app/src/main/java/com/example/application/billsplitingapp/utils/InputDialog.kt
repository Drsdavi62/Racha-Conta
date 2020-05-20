package com.example.application.billsplitingapp.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.example.application.billsplitingapp.R

class InputDialog(private val mContext: Context, title: String?) {
    private val mBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
    private lateinit var mDialog: AlertDialog
    private val mEditText: EditText = EditText(mContext)

    init {
        val container = FrameLayout(mContext)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.leftMargin = mContext.resources.getDimensionPixelSize(R.dimen.dp_30)
        params.rightMargin = mContext.resources.getDimensionPixelSize(R.dimen.dp_30)
        mEditText.layoutParams = params
        mEditText.maxLines = 1
        mEditText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        container.addView(mEditText)
        mBuilder.setView(container)
        mBuilder.setTitle(title)
    }

    fun show(listener: View.OnClickListener) {
        mBuilder.setPositiveButton(mContext.resources.getString(R.string.ok), null)
        mDialog = mBuilder.create()
        mEditText.requestFocus()
        try {
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } catch (e: Exception) {
        }
        mDialog.show()
        val btn = mDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btn.setOnClickListener(listener)
    }

    fun dismiss(){
        mDialog.dismiss()
    }

    var editText: String
        get() = mEditText.text.toString().trim { it <= ' ' }
        set(text) {
            mEditText.setText(text)
        }

}
