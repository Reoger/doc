package com.hut.reoger.doc.user.presener

import com.hut.reoger.doc.user.model.RegisterData

/**
 * Created by CM on 2018/1/26.
 */

interface IRegisterPresenter{
    fun doRegister(registerData: RegisterData)
}