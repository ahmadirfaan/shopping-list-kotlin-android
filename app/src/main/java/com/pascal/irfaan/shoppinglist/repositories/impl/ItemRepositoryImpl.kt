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

    override fun update(item: Item): Item {
        var oldItem = itemList.find {
            it.id == item.id
        }
        oldItem?.let {
            delete(it)
            add(item)
        }
        return item
    }

    override fun list(): ArrayList<Item> = itemList
}