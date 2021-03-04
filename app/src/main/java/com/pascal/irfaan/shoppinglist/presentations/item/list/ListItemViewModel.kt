package com.pascal.irfaan.shoppinglist.presentations.item.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.CoroutinesRoom
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener
import com.pascal.irfaan.shoppinglist.data.dao.impl.ItemRepositories
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.*

class ListItemViewModel(private val itemRepository: ItemRepositories) : ViewModel(), ItemClickListener {

    private var _validationItemList = MutableLiveData<ResourceState>()
    val validationItemList: LiveData<ResourceState>
        get() {
            return _validationItemList
        }
    val getAllItems = itemRepository.getAllItems
    val TOTAL_NUM_ITEMS = getAllItems.value?.size
    val ITEMS_DATA = getAllItems.value
    val ITEMS_PER_PAGE = 5
    val ITEM_REMAINING = TOTAL_NUM_ITEMS?.rem(ITEMS_PER_PAGE)
    val LAST_PAGE = TOTAL_NUM_ITEMS?.div(ITEMS_PER_PAGE)


    private var _paginationData = MutableLiveData<List<Item>>()
    val paginationData: LiveData<List<Item>>
        get() {
            return _paginationData
        }

    private var _itemUpdateLiveData = MutableLiveData<Item>()
    val itemUpdateLiveData: LiveData<Item>
        get() {
            return _itemUpdateLiveData
        }

    fun validationItemList() {
        CoroutineScope(Dispatchers.IO).launch {
            _validationItemList.postValue(ResourceState.loading())
            delay(2000)
            if (itemRepository.getAllItems.value?.isEmpty() == true) {
                _validationItemList.postValue(ResourceState.failure("DATA ITEM IS NULL"))
            } else {
                _validationItemList.postValue(ResourceState.success(true))
            }
        }
    }

    fun addItem(entity: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.addItem(entity)
        }
    }

    fun deleteItem(entity: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.deleteItem(entity)
        }
    }

    fun updateItem(entity: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.updateItem(entity)
            _itemUpdateLiveData.postValue(entity)
        }
    }

    fun deleteAllItem() {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.deleteAllItem()
        }
    }



    fun generatePage(currentPage: Int): List<Item> {
        val cp = currentPage - 1
        var startItem = cp * ITEMS_PER_PAGE
        var numOfData = ITEMS_PER_PAGE
        var pageData = ArrayList<Item>()
        if (cp == LAST_PAGE && ITEMS_PER_PAGE > 0) {
            val lastdata = (startItem + ITEM_REMAINING!!) - 1
            for (i in startItem..lastdata) {
                ITEMS_DATA?.get(i)?.let {
                    pageData.add(it) }
            }
        } else {
            val lastdata = (startItem + numOfData) - 1
            for (i in startItem..lastdata) {
                ITEMS_DATA?.get(i)?.let {
                    pageData.add(it)
                }
            }
        }
        _paginationData.value = pageData
        return pageData
    }



    override fun onDelete(item: Item) {
        deleteItem(item)
    }

    override fun onUpdate(item: Item) {
        _itemUpdateLiveData.value = item
    }


}