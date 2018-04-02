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
    fun doComment(comments:String,usr:String,doc_id:String)

    /**
     * 加载评论
     * 以doc_id为准
     */
    fun loadComments(doc_id:String)


}