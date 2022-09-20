package com.example.viewit

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME:String="MyDB"
const val DATABASE_VERSION:Int=1

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION)
{
    val DATABASE_TABLE:String="WordsForModel"
    val data:String="words"

    private val CREATE_DB_QUERY:String="CREATE TABLE "+DATABASE_TABLE+"("+data+" TEXT NOT NULL);"
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(CREATE_DB_QUERY)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}