package com.pascal.irfaan.shoppinglist.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ItemsResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: ItemsEntity,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
