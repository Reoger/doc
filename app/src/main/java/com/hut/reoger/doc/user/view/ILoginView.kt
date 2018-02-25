package com.hut.reoger.doc.user.view

/**
 * Created by CM on 2018/1/24.
 */

interface ILoginView {
    fun loginResult(code:Int, des:String)
    fun checkRememberPassWd(user:String,passwd:String)
}