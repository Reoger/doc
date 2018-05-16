package com.hut.reoger.doc.user.view

/**
 * Created by CM on 2018/1/24.
 */

interface ILoginView {
    companion object {
        val LOGIN_FILA = -1
        val LOGIN_SUCCUSS = 1
        val LOGIN_UNKONE = 0
    }

    fun loginResult(code:Int, des:String?)
    fun checkRememberPassWd(user:String,passwd:String)
}