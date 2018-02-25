package com.hut.reoger.doc.user.presener



/**
 * Created by CM on 2018/1/24.
 */
interface ILoginPresenter {
    fun doLogin(str:String,password:String)
    fun rememberPasswd(isRemember:Boolean,userName:String,password: String)
    fun checkRememberPasswd()
}