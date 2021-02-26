package com.pascal.irfaan.shoppinglist.repositories

import com.pascal.irfaan.shoppinglist.models.Item


interface ItemRepository {
    fun add(item: Item)
    fun delete(item: Item)
    fun update(item : Item) : Item
    fun list(): ArrayList<Item>
}