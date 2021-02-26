package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListItemViewModel(val itemRepository: ItemRepositoryImpl) : ViewModel(), ItemClickListener {

    private var _itemListLiveData = MutableLiveData<ArrayList<Item>>()
    val itemListLiveData: LiveData<ArrayList<Item>>
        get() {
            return _itemListLiveData
        }
    private var _validationItemList = MutableLiveData<ResourceState>()
    val validationItemList: LiveData<ResourceState>
        get() {
            return _validationItemList
        }

    private var _itemUpdateLiveData = MutableLiveData<Item>()
    val itemUpdateLiveData: LiveData<Item>
        get() {
            return _itemUpdateLiveData
        }


    fun addItemToList(item : Item) {
        itemRepository.add(item)
        loadItemData()
    }

    fun getItemList(): ArrayList<Item> = itemRepository.list()


    fun validationItemList() {
        GlobalScope.launch {
            _validationItemList.postValue(ResourceState.loading())
            delay(2000)
            if (itemRepository.list().isEmpty()) {
                _validationItemList.postValue(ResourceState.failure("ITEM IS NULL"))
            } else {
                _validationItemList.postValue(ResourceState.success(true))
            }
        }
    }

    override fun onDelete(item: Item) {
        itemRepository.delete(item)
        loadItemData()
    }

    override fun onUpdate(item: Item) {
        _itemUpdateLiveData.value = item
    }

    fun loadItemData() {
        _itemListLiveData.value = itemRepository.list()
    }

}