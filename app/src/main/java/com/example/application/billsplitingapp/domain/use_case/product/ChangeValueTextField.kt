package com.example.application.billsplitingapp.domain.use_case.product

import javax.inject.Inject

class ChangeValueTextField @Inject constructor() {
    operator fun invoke(
        previousText: String,
        futureText: String
    ): String {
        var finalText = futureText.replace(",", "")
        if (futureText.length > previousText.length) {
            if (finalText[2] == '0') {
                finalText = finalText.removeRange(2, 3)
            }
        } else if (futureText.length < previousText.length) {
            if (previousText.length <= 6) {
                finalText = finalText.substring(0, 2) + "0" + finalText.substring(2, finalText.length)
            }
        }
        finalText = finalText.substring(0, finalText.length - 2) + "," + finalText.subSequence(
            finalText.length - 2,
            finalText.length
        )

        return finalText
    }
}