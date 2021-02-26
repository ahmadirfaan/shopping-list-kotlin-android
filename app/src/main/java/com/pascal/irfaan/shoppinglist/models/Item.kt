package com.pascal.irfaan.shoppinglist.models

import android.os.Parcel
import android.os.Parcelable

data class Item(val id : String = "", val shoppingDate:String, val itemName : String, val quantity:String, val notes:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(itemName)
        dest?.writeString(shoppingDate)
        dest?.writeString(quantity)
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
