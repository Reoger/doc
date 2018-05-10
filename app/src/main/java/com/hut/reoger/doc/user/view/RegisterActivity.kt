package com.hut.reoger.doc.user.view

import android.os.Bundle
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.user.model.RegisterData
import com.hut.reoger.doc.user.model.RegisterInfo
import com.hut.reoger.doc.user.presener.IRegisterPresenter
import com.hut.reoger.doc.user.presener.RegisterPresenter
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.safe.Md5Tool
import kotlinx.android.synthetic.main.activity_register.*


/**
 * Created by CM on 2018/1/26.
 *
 */
class RegisterActivity: BaseActivity(), IRegisterView {


    override fun registerSuccessful(registerInfo: RegisterInfo) {
        log("注册成功")
        toast("注册成功")

    }

    override fun registerDenial(registerInfo: RegisterInfo) {
        log("注册拒绝")
        toast("注册拒绝")
    }

    override fun registerFail(statusCode: Int, apiErrorModel: ApiErrorModel) {
        log("注册失败")
        toast("注册失败")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun setActionBar() {
        setActivityTitle("注册")
    }

    override fun initView() {
        val presenter: IRegisterPresenter = RegisterPresenter(RegisterActivity@this,this)

        register_do.setOnClickListener({
            val passwd = Md5Tool.hashKey(register_password.text.toString())
            LogUtils.d("密码为passwd ${register_password.text}")
            val a = RegisterData(register_name.text.toString(),passwd,register_nickname.text.toString(),register_birthday.text.toString(),
                    register_email.text.toString(),register_introduction.text.toString())
            presenter.doRegister(a)
        })
    }

}