package com.pascal.irfaan.shoppinglist.presentations.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.pascal.irfaan.shoppinglist.R

class LoadingDialog {

    companion object {
        fun build(context: Context): AlertDialog {
            val inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
            val dialog = AlertDialog.Builder(context).setView(inflate).setCancelable(true).create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }
}  
