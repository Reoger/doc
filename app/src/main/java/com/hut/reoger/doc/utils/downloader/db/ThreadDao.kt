package com.hut.reoger.doc.utils.downloader.db

import com.hut.reoger.doc.utils.downloader.bean.ThreadInfo


/**
 * Date: 2018/3/5 11:04
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 数据库操作的接口类
 */

public interface ThreadDao{

    /**
     * 插入线程信息
     */
    fun insertThread(threadInfo: ThreadInfo)

    /**
     * 删除线程信息
     */
    fun deleteThread(url: String)

    /**
     * 更新线程信息
     */
    fun updateThread(url: String,thread_id:Int,finished:Long)

    /**
     * 查询文件的线程信息
     */
    fun getThread(url: String):List<ThreadInfo>

    /**
     * 判断线程是否存在
     */
    fun isExists(url: String,thread_id: Int)

}