package com.hut.reoger.doc.read.`interface`

/**
 * Created by reoger on 2018/4/2.
 * 用于fragment和activity之间的通信
 */
interface IDialogFragmentCallback{
    fun response(message:String,score:Int)
}