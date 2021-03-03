package com.pascal.irfaan.shoppinglist.data.dao.impl

import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.data.dao.ItemDao

class ItemRepositories(private val itemDao: ItemDao){

    val getAllItems = itemDao.getAllItems()

    suspend fun addItem(entity: Item) {
        itemDao.addItem(entity)
    }

    suspend fun deleteItem(entity: Item) {
        itemDao.deleteItem(entity)
    }

    suspend fun updateItem(entity: Item) {
        itemDao.updateItem(entity)
    }

    suspend fun deleteAllItem() {
        itemDao.deleteAllItem()
    }

}