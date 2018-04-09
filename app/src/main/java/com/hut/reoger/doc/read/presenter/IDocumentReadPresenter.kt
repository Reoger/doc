package com.hut.reoger.doc.read.presenter

import com.hut.reoger.doc.read.view.SuperFileView2

/**
 * Created by reoger on 2018/3/25.
 * 阅读界面的接口定义
 */

interface IDocumentReadPresenter {
    /**
     * 从网络上下载
     */
    fun downLoadFromNet(url: String, mSuperFileView2: SuperFileView2)

    /**
     * 添加评论
     */
    fun doComment(comments:String,usr:String,doc_id:String,score:Int)

    /**
     * 加载评论
     * 以doc_id为准
     */
    fun loadComments(doc_id:String)

    /**
     * 删除评论（通过文档id）
     */
    fun deleteComment(comment_id:Int)

    /**
     * 评论当前文档是否被收藏
     */
    fun isCurrentDocMarked(doc_id:String):Boolean

    /**
     * 取消收藏文档
     */
    fun cancelDocMarked(doc_id:String):Boolean

    /**
     * 收藏文档
     */
    fun markDoc(doc_id:String,user_id:String,doc_name:String):Boolean
}