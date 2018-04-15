package com.hut.reoger.doc.utils.db

import android.content.ContentValues
import android.content.Context
import com.hut.reoger.doc.bean.MarksBean
import java.util.ArrayList

/**
 * Created by reoger on 2018/4/8.
 * mark表的操作类
 */
class MarkDbImpl : IMarkDao {

    private var mDBHelper: DBHelper? = null

    companion object {
        val MARK_TABLE_NAME = "marks"
    }

    constructor(context: Context) {
        mDBHelper = DBHelper.getInstance(context)
    }

    @Synchronized
    override fun insertMark(marks: MarksBean): Boolean {
        val db = mDBHelper?.writableDatabase
        val values = ContentValues()
        values.put("doc_id", marks.doc_id)
        values.put("doc_name", marks.doc_name)
        values.put("mark_time", marks.mark_time)
        values.put("user_id", marks.user_id)
        var res = db?.insert(MARK_TABLE_NAME, null, values) ?: -1
        db?.close()
        return res != -1L
    }

    @Synchronized
    override fun deleteMark(doc_id: String): Boolean {
        val db = mDBHelper?.writableDatabase
        val res: Int = db?.delete(MARK_TABLE_NAME, "doc_id = ?", arrayOf(doc_id)) ?: 0
        db?.close()
        return res > 0
    }

    override fun getScollData(offest: Int, maxResult: Int): List<MarksBean> {
        val list = ArrayList<MarksBean>()
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from marks order by mark_time desc  limit ?,?", arrayOf(offest.toString(), maxResult.toString()))
        while (cursor!!.moveToNext()) {
            val item = MarksBean(cursor.getString(cursor.getColumnIndex("user_id")),
                    cursor.getString(cursor.getColumnIndex("doc_id")),
                    cursor.getString(cursor.getColumnIndex("doc_name")),
                    cursor.getLong(cursor.getColumnIndex("mark_time")))
            list.add(item)
        }
        cursor.close()
        db?.close()
        return list
    }

    /**
     * 判断指定的doc_id是否存在数据库中
     */
    override fun isMarks(doc_id: String): Boolean {
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from marks where doc_id =?", arrayOf(doc_id))
        cursor.use { cursor ->
            while (cursor != null && cursor.moveToNext()) {
                return true
            }
            return false
        }
    }

}