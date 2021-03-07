package com.pascal.irfaan.shoppinglist.utils

import com.pascal.irfaan.shoppinglist.data.models.ItemsEntity

interface ItemClickListener {

    fun onDelete(item: ItemsEntity)
    fun onUpdate(item: ItemsEntity)
}