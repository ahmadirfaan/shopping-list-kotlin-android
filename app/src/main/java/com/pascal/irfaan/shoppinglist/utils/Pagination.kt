package com.pascal.irfaan.shoppinglist.utils

import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl

class Pagination(val itemRepositoryImpl: ItemRepositoryImpl) {

    val TOTAL_NUM_ITEMS = itemRepositoryImpl.list().size
    val ITEMS_DATA = itemRepositoryImpl.list()
    val ITEMS_PER_PAGE = 5
    val ITEM_REMAINING = TOTAL_NUM_ITEMS % ITEMS_PER_PAGE
    val LAST_PAGE = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE

    fun generatePage(currentPage: Int): ArrayList<Item> {

        var startItem = currentPage * ITEMS_PER_PAGE
        var numOfData = ITEMS_PER_PAGE
        var pageData = ArrayList<Item>()
        if (currentPage == LAST_PAGE && ITEMS_PER_PAGE > 0) {
            val lastdata = (startItem + ITEM_REMAINING) - 1
            for (i in startItem..lastdata) {
                pageData.add(ITEMS_DATA[i])
            }
        } else {
            val lastdata = (startItem + numOfData) - 1
            for (i in startItem..lastdata) {
                pageData.add(ITEMS_DATA[i])
            }
        }
        return  pageData
    }
}

