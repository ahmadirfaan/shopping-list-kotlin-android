package com.pascal.irfaan.shoppinglist.utils

import com.pascal.irfaan.shoppinglist.data.models.Item

interface ItemClickListener {

    fun onDelete(item : Item)
    fun onUpdate(item : Item)
}