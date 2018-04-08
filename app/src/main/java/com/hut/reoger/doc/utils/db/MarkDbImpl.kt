package com.hut.reoger.doc.utils.db

import android.content.ContentValues
import android.content.Context
import com.hut.reoger.doc.bean.MarksBean
import java.util.ArrayList

/**
 * Created by reoger on 2018/4/8.
 * mark表的操作类
 */
class MarkDbImpl :IMarkDao{

    private var mDBHelper: DBHelper? = null

    companion object {
        val MARK_TABLE_NAME = "marks"
    }

    constructor(context: Context) {
        mDBHelper = DBHelper.getInstance(context)
    }

    @Synchronized
    override fun insertMark(marks: MarksBean) {
        val db = mDBHelper?.writableDatabase
        val values = ContentValues()
        values.put("doc_id",marks.doc_id)
        values.put("doc_name",marks.doc_name)
        values.put("mark_time",marks.mark_time)
        values.put("user_id",marks.user_id)
        db?.insert(MARK_TABLE_NAME,null,values)
    }

    @Synchronized
    override fun deleteMark(marks: MarksBean) {
        val db = mDBHelper?.writableDatabase
        db?.delete(MARK_TABLE_NAME,"where doc_id = ", arrayOf(marks.doc_id))
    }

    override fun getScollData(offest: Int, maxResult: Int): List<MarksBean> {
        val list = ArrayList<MarksBean>()
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from marks order by marks_time desc  limit ?,?", arrayOf(offest.toString(),maxResult.toString()))
        while (cursor!!.moveToNext()) {
           val item =  MarksBean( cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getString(cursor.getColumnIndex("doc_id")),
                    cursor.getString(cursor.getColumnIndex("doc_name")),
                    cursor.getInt(cursor.getColumnIndex("mark_time")))
            list.add(item)
        }
        cursor.close()
        return list
    }

    /**
     * 判断指定的doc_id是否存在数据库中
     */
    override fun isMarks(doc_id: String): Boolean {
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from marks where doc_id =?", arrayOf(doc_id))
        cursor.use { cursor ->
            while (cursor!=null && cursor.moveToNext()){
                return true
            }
            return false
        }
    }

}