package com.hut.reoger.doc.user.view.fragment

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.netWork.*

/**
 * Created by reoger on 2018/5/14.
 *
 */
class UserFragment : BaseFragment() {

    companion object {
        fun getInstance(): UserFragment {
            return UserFragment.innerClass.INSTANCE
        }
    }

    object innerClass {
        val INSTANCE: UserFragment = UserFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }

    override fun initView(mRootView: View) {
        val name: TextView = mRootView.findViewById(R.id.tv_user_name)
        val email: TextView = mRootView.findViewById(R.id.tv_user_email_show)
        val button: Button = mRootView.findViewById(R.id.bu_user_logout)

        App.instance.userInfo?.let {
            name.text =it.userName
            email.text = it.userId

        }
        button.setOnClickListener({
            //            App.instance.showDialog()
            if (App.instance.userInfo == null) {
                toast("用户未登录")
            } else {
                ApiClient.instance.service.logout(App.instance.userInfo!!.token)
                        .compose(NetworkScheduler.compose())
                        .subscribe(object : ApiResponse<ServiceReply>(activity!!) {
                            override fun success(data: ServiceReply) {
                                LogUtils.d("成功的消息${data.code} -> ${data.message}")
                                if (data.code == ResponseCode.RESULT_OK){
                                    toast("退出登录成功!")
                                    App.instance.userInfo = null
                                }else{
                                    toast("登录失败${data.message}")
                                }
                            }
                            override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                                toast("登录失败$apiErrorModel")
                            }
                        })
            }

        })


    }

}