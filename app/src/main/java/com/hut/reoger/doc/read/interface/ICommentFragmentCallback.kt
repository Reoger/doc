package com.hut.reoger.doc.read.`interface`

/**
 * Created by reoger on 2018/4/7.
 * fragment和activity通信的桥梁，这里利用他实现通知fragment数据 评论删除成功
 */
interface ICommentFragmentCallback{
    fun deletCommentSuccessful(pos:Int)
}
