package com.example.viewit.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize


@Entity(tableName = "WordsForModel")
data class WordsForModel(
//    @PrimaryKey(autoGenerate = true)
//    val id:Int,
    @PrimaryKey
    val words:String
) : Parcelable {
    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
        parcel.writeString(words)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordsForModel> {
        override fun createFromParcel(parcel: Parcel): WordsForModel {
            return WordsForModel(parcel)
        }

        override fun newArray(size: Int): Array<WordsForModel?> {
            return arrayOfNulls(size)
        }
    }
}