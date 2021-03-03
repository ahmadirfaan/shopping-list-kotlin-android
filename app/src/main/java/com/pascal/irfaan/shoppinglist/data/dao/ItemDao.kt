package com.pascal.irfaan.shoppinglist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pascal.irfaan.shoppinglist.data.models.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(entity: Item)

    @Delete
    suspend fun deleteItem(entity: Item)

    @Update
    suspend fun updateItem(entity : Item)

    @Query("DELETE FROM items")
    suspend fun deleteAllItem()

    @Query("SELECT * FROM items ORDER BY id asc")
    fun getAllItems(): LiveData<List<Item>>
}