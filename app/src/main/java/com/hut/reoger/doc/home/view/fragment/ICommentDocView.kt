package com.hut.reoger.doc.home.view.fragment

import com.hut.reoger.doc.read.bean.CommentsByDoc

/**
 * Created by reoger on 2018/4/15.
 */
interface ICommentDocView{

    fun loadCommentSuccessful(data: CommentsByDoc?)

    fun loadCommentFaile(error:String)

    fun deleteCommentSuccessful(position :Int)
}