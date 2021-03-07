package com.pascal.irfaan.shoppinglist.data.models

import com.google.gson.annotations.SerializedName

data class ItemsRequest(
	@field:SerializedName("itemName")
	val itemName: String,

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("notes")
	val notes: String,

	@field:SerializedName("dateShop")
	val dateShop: String
)
