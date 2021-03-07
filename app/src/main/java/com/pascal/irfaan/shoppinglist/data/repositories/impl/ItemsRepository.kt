package com.pascal.irfaan.shoppinglist.data.repositories

import com.pascal.irfaan.shoppinglist.data.api.ItemRepositoryApi
import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import retrofit2.Response
import retrofit2.http.*

interface ItemsRepository : ItemRepositoryApi {

}