package com.pascal.irfaan.shoppinglist.data.repositories

import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import retrofit2.Response
import retrofit2.http.*

interface ItemsRepository {

    suspend fun getAllShoppingList(): Response<ResponsePagination>
    suspend fun addItemShopping(request : ItemsRequest): Response<ItemsResponse>
    suspend fun deleteItemShopping(id : Int) : Response<ItemsResponse>
    suspend fun findItemById(id : Int) : Response<ItemsResponse>
}