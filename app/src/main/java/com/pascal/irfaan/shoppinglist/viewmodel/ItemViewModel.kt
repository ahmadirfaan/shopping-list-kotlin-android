package com.pascal.irfaan.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    val itemList: MutableList<Item> = ArrayList()
    private var _itemList = MutableLiveData<ResourceState>()
    val item: LiveData<ResourceState>
        get() {
            return _itemList
        }

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    fun inputValidation(vararg input: String) {
        GlobalScope.launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            for (i in input) {
                if (i.isBlank()) {
                    _inputValidation.postValue(ResourceState.failure("The Input Must not Empty"))
                    break
                } else {
                    _inputValidation.postValue(ResourceState.success(true))
                    continue
                }
            }

        }
    }

    fun validationItemList() {
        GlobalScope.launch {
            _itemList.postValue(ResourceState.loading())
            delay(2000)
            if (itemList.isEmpty()) {
                _itemList.postValue(ResourceState.failure("ITEM IS NULL"))
            } else {
                _itemList.postValue(ResourceState.success(itemList))
            }
        }
    }
}