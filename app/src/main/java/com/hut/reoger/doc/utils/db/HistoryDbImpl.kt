package com.hut.reoger.doc.utils.db

import android.content.Context
import com.hut.reoger.doc.search.bean.HitsItem
import android.content.ContentValues
import com.hut.reoger.doc.search.bean.Highlights
import com.hut.reoger.doc.search.bean.Source
import java.util.ArrayList


/**
 * Created by reoger on 2018/3/25.
 * 针对历史记录数据库的增删改查
 */
class HistoryDbImpl :IHistoryDao{


    var mDBHelper: DBHelper? = null

    companion object {
        val HISTORY_TABLE_NAME = "read_history"
    }

    constructor(context: Context) {
        mDBHelper = DBHelper.getInstance(context)
    }

    @Synchronized
    override fun insertReadHistory(histItem: HitsItem) {
        val db = mDBHelper?.writableDatabase
        val values = ContentValues()
        values.put("doc_index",histItem.Index)
        values.put("doc_type",histItem.Type)
        values.put("doc_id",histItem.Id)
        values.put("doc_score",histItem.Score)
        values.put("doc_down_link",histItem.Source.downLink)
        values.put("doc_update_time",histItem.Source.updateTime)
        values.put("doc_size",histItem.Source.size)
        values.put("doc_author",histItem.Source.author)
        values.put("doc_name",histItem.Source.name)
        values.put("doc_time",System.currentTimeMillis()) //这里修改成存储的时间
        values.put("doc_content",histItem.highlight.content.toString())

        db?.insert(HISTORY_TABLE_NAME,null,values)
    }

    @Synchronized
    override fun deleteReadHistory(_id: String) {
        val db = mDBHelper?.writableDatabase
        db?.delete(HISTORY_TABLE_NAME,"where doc_id = ", arrayOf(_id))
    }

    @Synchronized
    override fun updateReadHistory(histItem: HitsItem) {
        val db = mDBHelper?.writableDatabase
        val values = ContentValues()
        values.put("doc_index",histItem.Index)
        values.put("doc_type",histItem.Type)
//        values.put("doc_id",histItem.Id)
        values.put("doc_score",histItem.Score)
        values.put("doc_down_link",histItem.Source.downLink)
        values.put("doc_update_time",histItem.Source.updateTime)
        values.put("doc_size",histItem.Source.size)
        values.put("doc_author",histItem.Source.author)
        values.put("doc_name",histItem.Source.name)
        values.put("doc_time",System.currentTimeMillis())
        values.put("doc_content",histItem.highlight.content.toString())

        db?.update(HISTORY_TABLE_NAME,values,"where doc_id = ?", arrayOf(histItem.Id))
    }

    /**
     * 返回所有的数据
     */
    override fun getReadHistory(): List<HitsItem> {
        val list = ArrayList<HitsItem>()
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from $HISTORY_TABLE_NAME order by doc_time desc",null)

        while (cursor!!.moveToNext()) {
            val item  = HitsItem(Highlights(listOf(cursor.getString(cursor.getColumnIndex("content")))),cursor.getString(cursor.getColumnIndex("doc_index")),
                    cursor.getString(cursor.getColumnIndex("doc_type")), Source(cursor.getString(cursor.getColumnIndex("doc_down_link")),
                    cursor.getLong(cursor.getColumnIndex("doc_update_time")),cursor.getInt(cursor.getColumnIndex("doc_size")),
                    cursor.getString(cursor.getColumnIndex("doc_author")),cursor.getString(cursor.getColumnIndex("doc_name")),
                    cursor.getInt(cursor.getColumnIndex("doc_time")) )
                    )
            list.add(item)
        }
        return list
    }

    /**
     * 分页返回查询的内容
     */
    override fun getScollData(offest: Int, maxResult: Int): List<HitsItem> {
        val list = ArrayList<HitsItem>()
        val db = mDBHelper?.readableDatabase
        val cursor = db?.rawQuery("select * from $HISTORY_TABLE_NAME order by doc_time desc  limit ?,?", arrayOf(offest.toString(),maxResult.toString()))
        while (cursor!!.moveToNext()) {
            val item  = HitsItem(Highlights(listOf(cursor.getString(cursor.getColumnIndex("content")))),cursor.getString(cursor.getColumnIndex("doc_index")),
                    cursor.getString(cursor.getColumnIndex("doc_type")), Source(cursor.getString(cursor.getColumnIndex("doc_down_link")),
                    cursor.getLong(cursor.getColumnIndex("doc_update_time")),cursor.getInt(cursor.getColumnIndex("doc_size")),
                    cursor.getString(cursor.getColumnIndex("doc_author")),cursor.getString(cursor.getColumnIndex("doc_name")),
                    cursor.getInt(cursor.getColumnIndex("doc_time")) )
            )
            list.add(item)
        }
        return list
    }

}