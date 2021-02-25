package com.pascal.irfaan.shoppinglist.repositories.impl

import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.ItemRepository

class ItemRepositoryImpl : ItemRepository{
    companion object {
        var itemList = ArrayList<Item>()

    }
    override fun add(item: Item) {
        itemList.add(item)
    }

    override fun delete(item: Item) {
        val itemDeleted = itemList.indexOf(item)
        itemList.removeAt(itemDeleted)
    }
    override fun list(): ArrayList<Item> = itemList
}