package com.pascal.irfaan.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel(), ItemClickListener {

    private var itemList = ArrayList<Item>()
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

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    fun addItemToList(item : Item) {
        itemList.add(item)
        _itemListLiveData.value = itemList
    }

    fun getItemList(): ArrayList<Item> = itemList

    fun inputValidation(vararg input: String) {
        GlobalScope.launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            var check = ArrayList<Int>()
            for (i in input) {
                if (!i.isEmpty() || !i.isBlank()) {
                    check.add(1)
                }
            }
            if(check.size == input.size) {
                _inputValidation.postValue(ResourceState.success(true))
            } else {
                _inputValidation.postValue(ResourceState.failure("INPUT MUST NOT EMPTY"))
            }

        }
    }

    fun validationItemList() {
        GlobalScope.launch {
            _validationItemList.postValue(ResourceState.loading())
            delay(2000)
            if (itemList.isEmpty()) {
                _validationItemList.postValue(ResourceState.failure("ITEM IS NULL"))
            } else {
                _validationItemList.postValue(ResourceState.success(itemList))
            }
        }
    }

    override fun onDelete(item: Item) {
        val itemDelete = itemList.indexOf(item)
        itemList.removeAt(itemDelete)
        _itemListLiveData.value = itemList
    }

}