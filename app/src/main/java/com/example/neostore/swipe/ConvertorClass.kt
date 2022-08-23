package com.example.neostore.swipe

import java.text.DecimalFormat
import java.text.NumberFormat

class ConvertorClass {

    fun convertorfunction(data : Number):String{
        val nf = NumberFormat.getCurrencyInstance()
        val pattern = (nf as DecimalFormat).toPattern()
        val newPattern = pattern.replace("\u00A4", "\u20B9 ").trim { it <= ' ' }
        val newFormat: NumberFormat = DecimalFormat(newPattern)
        val answer = newFormat.format(data).trim()
        return answer
    }
}

