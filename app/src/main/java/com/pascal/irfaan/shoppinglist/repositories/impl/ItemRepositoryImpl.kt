package com.pascal.irfaan.shoppinglist.repositories.impl

import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.ItemRepository
import java.util.*
import kotlin.collections.ArrayList

class ItemRepositoryImpl : ItemRepository{
    val itemList = makeItemsPaginationTest()
    
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
    
    fun makeItemsPaginationTest(): ArrayList<Item> {
        val itemsData = ArrayList<Item>()
        for(i in 1..22) {
            val items = Item(shoppingDate = "s + $i", itemName = "item $i", quantity =  "$i", notes = "note $i")
            itemsData.add(items)
        }
        return itemsData
    }
}