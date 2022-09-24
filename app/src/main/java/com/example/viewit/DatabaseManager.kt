package com.example.viewit

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabaseManager(context: Context) {

    lateinit var context:Context
    val dbHelper:DatabaseHelper= DatabaseHelper(context)
    val database:SQLiteDatabase=dbHelper.writableDatabase

    public fun open():DatabaseManager
    {

        return this
    }
    public fun close()
    {
        dbHelper.close()
    }
    public fun insert(data:String)
    {
        var cValues:ContentValues= ContentValues()
        cValues.put(dbHelper.data,data)
        database.insert(dbHelper.DATABASE_TABLE,null,cValues)
    }
    public fun fetch(): Cursor {
        val columns = emptyArray<String>()
        var cursor:Cursor=database.query(dbHelper.DATABASE_TABLE,columns,null,null,null,null,null)
        if(cursor!=null)
        {
            cursor.moveToFirst()
        }
        return cursor
    }
}