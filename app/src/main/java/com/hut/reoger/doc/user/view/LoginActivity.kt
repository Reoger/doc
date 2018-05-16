package com.hut.reoger.doc.user.view


import android.os.Bundle

import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.view.HomeActivity
import com.hut.reoger.doc.user.presener.ILoginPresenter
import com.hut.reoger.doc.user.presener.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by CM on 2018/1/24.
 *
 */
class LoginActivity : BaseActivity(), ILoginView {


    var presenter: ILoginPresenter?= null

    override fun checkRememberPassWd(user: String, passwd: String) {
        edit_username.setText(user)
        edit_password.setText(passwd)
        user_remember.isChecked = true
    }


    override fun loginResult(code: Int, des: String) {
        when (code) {
            ILoginView.LOGIN_SUCCUSS ->{
                toast(getString(R.string.login_success))
                presenter?.rememberPasswd(user_remember.isChecked,edit_username.text?.toString()!!, edit_password.text?.toString()!!)
                openActivityAndCloseThis(HomeActivity::class.java)
//                finish()
            }
            ILoginView.LOGIN_FILA ->{
                toast(getString(R.string.login_fail)+des)
            }
            else ->{
              toast(getString(R.string.login_unknow_error))
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun setActionBar() {
        setActivityTitle(getString(R.string.user_login))
    }

    override fun initView() {

        presenter = LoginPresenter(LoginActivity@ this, this)
        presenter?.checkRememberPasswd()
        button_login.setOnClickListener({
            // todo 这里还需要做数据控制。暂时先不做
            presenter?.doLogin(edit_username.text?.toString()!!, edit_password.text?.toString()!!)
        })

        user_register.setOnClickListener({
            openActivityAndCloseThis(RegisterActivity::class.java)
        })
    }

}