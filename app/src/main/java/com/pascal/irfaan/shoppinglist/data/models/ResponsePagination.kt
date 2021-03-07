package com.pascal.irfaan.shoppinglist.data.models

data class ResponsePagination(
	val code: Int,
	val data: DataPagination,
	val message: Any,
	val timestamp: String
)
