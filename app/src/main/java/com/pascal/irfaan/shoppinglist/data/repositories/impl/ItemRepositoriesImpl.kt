package com.pascal.irfaan.shoppinglist.data.repositories.impl

import com.pascal.irfaan.shoppinglist.data.api.RetrofitInstance
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.data.dao.ItemDao
import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import com.pascal.irfaan.shoppinglist.data.repositories.ItemsRepository
import retrofit2.Response

class ItemRepositoriesImpl() : ItemsRepository{
    override suspend fun getAllShoppingList(): Response<ResponsePagination> = RetrofitInstance.shoppingApi.getAllShoppingList()

    override suspend fun addItemShopping(request: ItemsRequest): Response<ItemsResponse> {
        return RetrofitInstance.shoppingApi.addItemShopping(request)
    }

    override suspend fun deleteItemShopping(id: Int): Response<ItemsResponse> {
        return RetrofitInstance.shoppingApi.deleteItemShopping(id)

    }

    override suspend fun findItemById(id: Int): Response<ItemsResponse> {
        return RetrofitInstance.shoppingApi.findItemById(id)
    }

    override suspend fun updateItemById(id: Int, request: ItemsRequest): Response<ItemsResponse> {
        return RetrofitInstance.shoppingApi.updateItemById(id, request)
    }

}