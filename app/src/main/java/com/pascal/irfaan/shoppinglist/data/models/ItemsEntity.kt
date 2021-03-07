package com.pascal.irfaan.shoppinglist.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsEntity(
	val itemName: String,
	val createdDate: String,
	val quantity: Int,
	val notes: String,
	val modifiedDate: String,
	val id: Int,
	val dateShop: String
) : Parcelable
