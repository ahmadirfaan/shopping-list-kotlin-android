package com.pascal.irfaan.shoppinglist.data.api

import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface ItemRepositoryApi {
    suspend fun getAllShoppingList(): Response<ResponsePagination>
    suspend fun addItemShopping(request : ItemsRequest): Response<ItemsResponse>
    suspend fun deleteItemShopping(id : Int) : Response<ItemsResponse>
    suspend fun findItemById(id : Int) : Response<ItemsResponse>
    suspend fun updateItemById(id :Int, request : ItemsRequest) : Response<ItemsResponse>
}