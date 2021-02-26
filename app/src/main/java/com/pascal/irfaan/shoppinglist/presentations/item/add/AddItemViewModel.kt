package com.pascal.irfaan.shoppinglist.presentations.item.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.irfaan.shoppinglist.utils.ResourceState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddItemViewModel : ViewModel(){

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }


    fun inputValidation(vararg input: String) {
        GlobalScope.launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            val check = ArrayList<Int>()
            for (i in input) {
                if (i.isNotEmpty() || i.isNotBlank()) {
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
}