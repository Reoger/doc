package com.hut.reoger.doc.user.presener

import android.content.Context
import android.content.SharedPreferences
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.user.Constance
import com.hut.reoger.doc.user.model.LoginInfo
import com.hut.reoger.doc.user.view.ILoginView
import com.hut.reoger.doc.utils.netWork.*
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent

/**
 * Created by CM on 2018/1/24
 * 采用mvp架构
 * .
 */

class LoginPresenter(var mContext: RxAppCompatActivity?, var longview: ILoginView?) : ILoginPresenter {
    override fun checkRememberPasswd() {
        val sp: SharedPreferences = mContext?.getSharedPreferences(Constance.REMEMBER_PASSWD, Context.MODE_PRIVATE)!!
        val isRemember = sp.getBoolean(Constance.ISEMBER, false)
        if (isRemember) {
            val user = sp.getString(Constance.USER, "")
            val password = sp.getString(Constance.PASSWORD, "")
            longview?.checkRememberPassWd(user, password)
        }
    }


    override fun rememberPasswd(isRemember: Boolean, userName: String, password: String) {
        val sp: SharedPreferences = mContext?.getSharedPreferences(Constance.REMEMBER_PASSWD, Context.MODE_PRIVATE)!!
        val edit = sp.edit()
        if (isRemember) {
            edit.putBoolean(Constance.ISEMBER, true)
            edit.putString(Constance.USER, userName)
            edit.putString(Constance.PASSWORD, password)
        } else {
            edit.putBoolean(Constance.ISEMBER, false)
        }
        edit.apply()
        edit.commit()

    }


    override fun doLogin(str: String, password: String) {
        ApiClient.instance.service.login(str, password)
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(mContext!!, event = ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<LoginInfo>(mContext!!) {
                    override fun success(data: LoginInfo) {
                        if (data.code == ResponseCode.RESULT_OK) {
                            longview?.apply {
                                App.instance.userInfo = data.data
                                this.loginResult(ILoginView.LOGIN_SUCCESS, mContext?.getString(R.string.login_success) + data.data.toString())
                            }
                        } else {
                            longview?.apply {
                                this.loginResult(ILoginView.LOGIN_FAIL, mContext?.getString(R.string.login_fail))
                            }
                        }

                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        longview?.apply {
                            this.loginResult(ILoginView.LOGIN_FAIL, mContext?.getString(R.string.login_fail))
                        }
                    }
                })
    }

}