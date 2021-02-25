package com.pascal.irfaan.shoppinglist.utils

import com.pascal.irfaan.shoppinglist.models.Item

interface ItemClickListener {

    fun onDelete(item : Item)
}