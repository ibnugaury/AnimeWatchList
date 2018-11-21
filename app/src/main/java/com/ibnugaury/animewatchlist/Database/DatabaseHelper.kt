package com.ibnugaury.animewatchlist.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ibnugaury.animewatchlist.Model.ModelData

class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx, DatabaseConstant.DATABASE_NAME, null, DatabaseConstant.DATABASE_VERSION) {

    companion object {
        private lateinit var INSTANCE: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database" , "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): DatabaseHelper {
            INSTANCE = DatabaseHelper(ctx)
            return INSTANCE
        }

        fun insertData(modelData: ModelData): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstant.ROW_TITLE, modelData.title)
            values.put(DatabaseConstant.ROW_YEAR, modelData.year)
            values.put(DatabaseConstant.ROW_GENRE, modelData.genre)
            return database.insert(DatabaseConstant.DATABASE_TABEL, null, values)
        }

        fun updateData(modelData: ModelData): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstant.ROW_TITLE, modelData.title)
            values.put(DatabaseConstant.ROW_YEAR, modelData.year)
            values.put(DatabaseConstant.ROW_GENRE, modelData.genre)
            return database.update(DatabaseConstant.DATABASE_TABEL, values, "${DatabaseConstant.ROW_ID} = ${modelData.id}", null)
        }

        fun getAllData(): MutableList<ModelData> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<ModelData> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DatabaseConstant.DATABASE_TABEL}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val watchlist = ModelData()
                        watchlist.id = cur.getInt(cur.getColumnIndex(DatabaseConstant.ROW_ID))
                        watchlist.title = cur.getString(cur.getColumnIndex(DatabaseConstant.ROW_TITLE))
                        watchlist.year = cur.getString(cur.getColumnIndex(DatabaseConstant.ROW_YEAR))
                        watchlist.genre = cur.getString(cur.getColumnIndex(DatabaseConstant.ROW_GENRE))
                        data.add(watchlist)
                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            return database.delete(DatabaseConstant.DATABASE_TABEL, "${DatabaseConstant.ROW_ID} = $id", null)
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DatabaseConstant.QUERY_CREATE)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DatabaseConstant.QUERY_UPGRADE)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}