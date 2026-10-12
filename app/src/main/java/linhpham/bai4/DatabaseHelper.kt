package linhpham.bai4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MSSV = "mssv"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_AVATAR = "avatar"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MSSV TEXT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_AVATAR TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertStudent(mssv: String, name: String, email: String, avatar: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MSSV, mssv)
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
            put(COLUMN_AVATAR, avatar)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student(
                    id = cursor.getInt(0),
                    mssv = cursor.getString(1),
                    name = cursor.getString(2),
                    email = cursor.getString(3),
                    avatar = cursor.getString(4)
                )
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return studentList
    }

    fun updateStudent(id: Int, mssv: String, name: String, email: String, avatar: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MSSV, mssv)
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
            put(COLUMN_AVATAR, avatar)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteStudent(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}