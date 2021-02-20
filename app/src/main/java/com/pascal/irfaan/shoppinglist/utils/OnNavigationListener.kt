package com.pascal.irfaan.shoppinglist.utils

import com.pascal.irfaan.shoppinglist.models.Item

interface OnNavigationListener {

    fun addShop(itemList: MutableList<Item>)
}