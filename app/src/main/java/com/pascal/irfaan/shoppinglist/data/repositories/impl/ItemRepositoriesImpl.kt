package com.pascal.irfaan.shoppinglist.data.repositories.impl

import com.pascal.irfaan.shoppinglist.data.api.ShoppingApi
import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import com.pascal.irfaan.shoppinglist.data.repositories.ItemsRepository
import retrofit2.Response
import javax.inject.Inject

class ItemRepositoriesImpl @Inject constructor(private val shoppingApi: ShoppingApi) : ItemsRepository {
    override suspend fun getAllShoppingList(): Response<ResponsePagination> = shoppingApi.getAllShoppingList()

    override suspend fun addItemShopping(request: ItemsRequest): Response<ItemsResponse> {
        return shoppingApi.addItemShopping(request)
    }

    override suspend fun deleteItemShopping(id: Int): Response<ItemsResponse> {
        return shoppingApi.deleteItemShopping(id)

    }

    override suspend fun findItemById(id: Int): Response<ItemsResponse> {
        return shoppingApi.findItemById(id)
    }

    override suspend fun updateItemById(id: Int, request: ItemsRequest): Response<ItemsResponse> {
        return shoppingApi.updateItemById(id, request)
    }

}