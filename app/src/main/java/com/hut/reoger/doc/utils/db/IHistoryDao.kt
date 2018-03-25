package com.hut.reoger.doc.utils.db

import com.hut.reoger.doc.search.bean.HitsItem

/**
 * Created by reoger on 2018/3/25.
 */
interface IHistoryDao{

    /**
     * 插入浏览历史信息
     */
    fun insertReadHistory(histItem: HitsItem)

    /**
     * 删除浏览历史
     */
    fun deleteReadHistory(_id:String)

    /**
     * 更新浏览历史
     */
    fun updateReadHistory(histItem: HitsItem)

    /**
     * 获取浏览历史
     */
    fun  getReadHistory():List<HitsItem>

    /**
     * 分页获取浏览历史数据
      */
    fun getScollData( offest:Int,  maxResult:Int):List<HitsItem>
}