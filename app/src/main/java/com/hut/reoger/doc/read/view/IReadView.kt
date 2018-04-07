package com.hut.reoger.doc.read.view

import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.read.bean.CommentsByDoc

/**
 * Created by reoger on 2018/3/25.
 * 阅读界面的接口
 */
interface IReadView{
    /**
     * 更新下载进度
     */
    fun updateProgress(progress:Int)

    /**
     * 评论成功
     */
    fun commentSuccessful(data: ServiceReply)

    /**
     * 评论失败
     */
    fun commentFail(error:String)

    /**
     * 评论加载成功
     */
    fun loadCommentSuccessful(data: CommentsByDoc?)

    /**
     * 删除评论成功
     */
    fun deleteCommentSuccessful()

    /**
     * 删除评论失败
     */
    fun deleteCommentFail(error:String)
}