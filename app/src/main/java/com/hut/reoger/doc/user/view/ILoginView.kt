package com.hut.reoger.doc.user.view

/**
 * Created by CM on 2018/1/24.
 */

interface ILoginView {
    companion object {
        const val LOGIN_FAIL = -1
        const val LOGIN_SUCCESS = 1
        const val LOGIN_UNKONE = 0
    }

    fun loginResult(code:Int, des:String?)
    fun checkRememberPassWd(user:String,passwd:String)
}