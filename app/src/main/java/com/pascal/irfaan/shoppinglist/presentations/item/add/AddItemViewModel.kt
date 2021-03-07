package com.pascal.irfaan.shoppinglist.presentations.item.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.repositories.ItemsRepository
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddItemViewModel(private val repository: ItemsRepository) : ViewModel() {

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    private var _addItemShoppingLiveData = MutableLiveData<ResourceState>()
    val addItemShoppingLiveData: LiveData<ResourceState>
        get() {
            return _addItemShoppingLiveData
        }


    fun inputValidation(vararg input: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            val check = ArrayList<Int>()
            for (i in input) {
                if (i.isNotEmpty() || i.isNotBlank()) {
                    check.add(1)
                }
            }
            if (check.size == input.size) {
                _inputValidation.postValue(ResourceState.success(true))
            } else {
                _inputValidation.postValue(ResourceState.failure("INPUT MUST NOT EMPTY"))
            }

        }
    }

    fun addItemShopping(request: ItemsRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            _addItemShoppingLiveData.postValue(ResourceState.loading())
            val response = repository.addItemShopping(request)
            if(response.isSuccessful) {
                val itemResponse = response.body()
                _addItemShoppingLiveData.postValue(itemResponse?.let {
                    ResourceState.success(it)
                })
            } else {
                _addItemShoppingLiveData.postValue(ResourceState.failure(response.message()))
            }
        }
    }
}