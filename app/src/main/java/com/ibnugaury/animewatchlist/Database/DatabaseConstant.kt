package com.ibnugaury.animewatchlist.Database

class DatabaseConstant {
    companion object {

        val DATABASE_NAME = "DB_NAME"
        val DATABASE_VERSION = 1

        val DATABASE_TABEL = "DB_TABEL"
        val ROW_ID = "_id"
        val ROW_TITLE = "title"
        val ROW_YEAR = "year"
        val ROW_GENRE = "genre"

        val QUERY_CREATE = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_TITLE TEXT , $ROW_YEAR TEXT , $ROW_GENRE TEXT)"
        val QUERY_UPGRADE = "DROP TABLE IF EXISTS $DATABASE_TABEL"

    }
}