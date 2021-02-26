package com.pascal.irfaan.shoppinglist.presentations.item.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.ItemRepository

class UpdateItemViewModel(private val repository: ItemRepository) : ViewModel() {
    private var _updateStatus = MutableLiveData<Item>()
    val updateStatus : LiveData<Item>
    get() {
        return _updateStatus
    }

    fun OnUpdate(item : Item) {
        val itemUpdated = repository.update(item)
        _updateStatus.value = itemUpdated
    }
}