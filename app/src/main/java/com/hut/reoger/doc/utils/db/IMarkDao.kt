package com.hut.reoger.doc.utils.db

import com.hut.reoger.doc.bean.MarksBean

/**
 * Created by reoger on 2018/4/8.
 * 定义收藏的数据库接口
 * 这里保存到数据库的数据都将是已经收藏的文档
 */
interface IMarkDao{
    /**
     * 插入收藏文档
     */
    fun insertMark(marks:MarksBean)

    /**
     * 删除收藏文档
     */
    fun deleteMark(marks:MarksBean)

    /**
     * 查询收藏的文档
     */
    fun  getScollData( offest:Int,  maxResult:Int):List<MarksBean>

    /**
     * 判断文档是否被收藏
     */
    fun isMarks(doc_id:String):Boolean


}