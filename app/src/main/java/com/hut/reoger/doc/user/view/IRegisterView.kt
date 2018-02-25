package com.hut.reoger.doc.user.view

import com.hut.reoger.doc.user.model.RegisterInfo
import com.hut.reoger.doc.utils.netWork.ApiErrorModel

/**
 * Created by CM on 2018/1/26.
 */
interface IRegisterView{
    fun registerSuccessful(registerInfo: RegisterInfo)
    fun registerDenial(registerInfo: RegisterInfo)
    fun registerFail(statusCode: Int, apiErrorModel: ApiErrorModel)
}