package com.hut.reoger.doc.home.presenter

/**
 * Created by reoger on 2018/4/15.
 */
interface ICommentPresenter{
    fun getMarkListByUsrId(use_id:String)

    fun deleteMarkComment(comment_id:Int,pos:Int)
}