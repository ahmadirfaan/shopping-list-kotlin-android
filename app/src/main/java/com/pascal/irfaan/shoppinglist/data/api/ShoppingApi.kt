package com.pascal.irfaan.shoppinglist.data.api

import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import retrofit2.Response
import retrofit2.http.*

interface ShoppingApi {

    @GET("/shopping?size=15")
    suspend fun getAllShoppingList(): Response<ResponsePagination>

    @POST("/shopping")
    suspend fun addItemShopping(@Body request : ItemsRequest): Response<ItemsResponse>

    @DELETE("/shopping/{id}")
    suspend fun deleteItemShopping(@Path("id") id : Int) : Response<ItemsResponse>

    @GET("/shopping/{id}")
    suspend fun findItemById(@Path("id") id : Int) : Response<ItemsResponse>

}