package com.pascal.irfaan.shoppinglist.data.models

import com.google.gson.annotations.SerializedName

data class DataPagination(
	@field:SerializedName("total")
	val total: Int,
	@field:SerializedName("size")
	val size: Int,
	@field:SerializedName("page")
	val page: Int,
	@field:SerializedName("list")
	val list: List<ItemsEntity>
)