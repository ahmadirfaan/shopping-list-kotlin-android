package com.pascal.irfaan.shoppinglist.utils

import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.models.Item

class ItemViewModel : ViewModel() {

    val itemList:MutableList<Item> = ArrayList()

    fun inputValidation(input : String) : Boolean =  !input.isNullOrBlank()
}