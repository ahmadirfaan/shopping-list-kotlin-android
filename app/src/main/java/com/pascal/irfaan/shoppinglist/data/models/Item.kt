package com.pascal.irfaan.shoppinglist.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "shopping_date") val shoppingDate:String,
    @ColumnInfo(name = "item_name") val itemName : String,
    @ColumnInfo(name = "quantity") val quantity:Int,
    @ColumnInfo(name = "notes") val notes:String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(itemName)
        dest?.writeString(shoppingDate)
        dest?.writeInt(quantity)
        dest?.writeString(notes)
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}
