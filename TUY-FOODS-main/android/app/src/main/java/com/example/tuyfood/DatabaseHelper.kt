package com.example.tuyfood

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "TuyFood.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(
        name: String,
        email: String,
        password: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)

        val result = db.insert(
            TABLE_USERS,
            null,
            values
        )

        db.close()

        return result != -1L
    }

    fun checkUser(
        email: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password)
        )

        val exists = cursor.count > 0

        cursor.close()
        db.close()

        return exists
    }
}