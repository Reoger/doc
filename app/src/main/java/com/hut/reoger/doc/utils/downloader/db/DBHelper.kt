package com.hut.reoger.doc.utils.downloader.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Date: 2018/3/2 21:57
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: TODO
 */

class DBHelper : SQLiteOpenHelper {

    private val SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start long,end long,finished long)"
    private  val  SQL_DROP = "drop table if exists thread_info"

   private  constructor(context: Context,name:String,version:Int): super(context,name,null,version){

    }

    companion object {
        private const val DB_NAME   :String = "download.db"
        private const val DB_VERSION:Int = 1
        fun getInstance(context: Context): DBHelper {
            val instance: DBHelper by lazy { DBHelper(context, DB_NAME, DB_VERSION) }
            return instance
        }
    }




    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP)
        db?.execSQL(SQL_CREATE)
    }

}