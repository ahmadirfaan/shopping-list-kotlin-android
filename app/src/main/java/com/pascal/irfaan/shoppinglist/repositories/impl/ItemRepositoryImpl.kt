package com.pascal.irfaan.shoppinglist.repositories.impl

import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.ItemRepository
import java.util.*
import kotlin.collections.ArrayList

class ItemRepositoryImpl : ItemRepository{
    companion object {
        var itemList = ArrayList<Item>()

    }
    override fun add(item: Item) {
        if(item.id.isNullOrBlank()) {
            val itemWithId = item.copy(id = UUID.randomUUID().toString())
            itemList.add(itemWithId)
        } else {
            itemList.add(item)
        }
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