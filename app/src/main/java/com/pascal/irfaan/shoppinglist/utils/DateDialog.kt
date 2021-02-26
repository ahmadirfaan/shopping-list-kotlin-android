package com.pascal.irfaan.shoppinglist.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

object DateDialog {
    val getDate = Calendar.getInstance()
    val year = getDate.get(Calendar.YEAR)
    val month = getDate.get(Calendar.MONTH)
    val day = getDate.get(Calendar.DAY_OF_MONTH)
    private val formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)


    fun show(context : Context, callback : (dataParsed : String) -> Unit) {
        val datepicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth ->
            val selectDate = Calendar.getInstance()
            selectDate.set(Calendar.YEAR, year)
            selectDate.set(Calendar.MONTH, month)
            selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date = formatDate.format(selectDate.time)
            callback(date)
        } ,year, month, day)
        datepicker.show()
    }
}