package com.hut.reoger.doc.utils.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Created by reoger on 2018/3/25.
 * 浏览记录保存的数据库
 */
class DBHelper: SQLiteOpenHelper{

    private val SQL_CREATE_HISTORY = "create table read_history(_id integer primary key autoincrement,\n" +
            "doc_index text,doc_type text,doc_id text,doc_score text,doc_down_link text,doc_update_time INTEGER,\n" +
            "doc_size long,doc_author text,doc_name text,doc_time integer,doc_content text)"
    private val SQL_CREATE_MARKS = "create table marks(_id integer primary key autoincrement,"+
            "doc_id text,doc_name text,mark_time integer,user_id integer)"
    private  val  SQL_DROP_HISTORY = "drop table if exists read_history"
    private  val  SQL_DROP_MARKS = "drop table if exists marks"

    companion object {
         const val DB_NAME   :String = "history.db"
        private const val DB_VERSION:Int = 1
        fun getInstance(context: Context): DBHelper {
            val instance: DBHelper by lazy { DBHelper(context, DB_NAME, DB_VERSION) }
            return instance
        }
    }

    private  constructor(context: Context, name:String, version:Int): super(context,name,null,version){

    }

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(SQL_CREATE_HISTORY)
       db?.execSQL(SQL_CREATE_MARKS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_HISTORY)
        db?.execSQL(SQL_DROP_MARKS)

        db?.execSQL(SQL_CREATE_HISTORY)
        db?.execSQL(SQL_CREATE_MARKS)
    }

}