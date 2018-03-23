package com.hut.reoger.doc.utils.downloader.db

import android.content.Context
import com.hut.reoger.doc.utils.downloader.bean.ThreadInfo
import java.util.*

/**
 * Date: 2018/3/5 11:04
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 数据库的实现类，实现具体的增删改查
 */

class ThreadDaoImple : ThreadDao {

    var mDBHelper: DBHelper? = null

    constructor(context: Context) {
        mDBHelper = DBHelper.getInstance(context)
    }

    @Synchronized
    override fun insertThread(threadInfo: ThreadInfo) {
        val db = mDBHelper!!.writableDatabase

        db.execSQL(
                "insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                arrayOf(threadInfo.id,
                        threadInfo.url,
                        threadInfo.start,
                        threadInfo.end,
                        threadInfo.finished
                ))
        db.close()
    }

    @Synchronized
    override fun deleteThread(url: String) {
        val db = mDBHelper!!.writableDatabase

        db.execSQL(
                "delete from  thread_info where url = ? ",
                arrayOf(url))
        db.close()
    }

    @Synchronized
    override fun updateThread(url: String, thread_id: Int, finished: Long) {
        val db = mDBHelper!!.writableDatabase

        db.execSQL(
                "update thread_info set finished = ?  where url = ? and thread_id = ?",
                arrayOf(finished, url, thread_id))
        db.close()
    }

    override fun getThread(url: String): List<ThreadInfo> {
        val list = ArrayList<ThreadInfo>()
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from thread_info where url=?", arrayOf(url))
        while (cursor!!.moveToNext()) {
            val threadInfo = ThreadInfo(cursor.getInt(cursor.getColumnIndex("thread_id")),
                    cursor.getString(cursor.getColumnIndex("url")),
                    cursor.getLong(cursor.getColumnIndex("start")),
                    cursor.getLong(cursor.getColumnIndex("end")),
                    cursor.getLong(cursor.getColumnIndex("finished"))
            )
            list.add(threadInfo)
        }
        return list
    }


    override fun isExists(url: String, thread_id: Int) {
        // 暂时先不实现
    }

}