package com.hut.reoger.doc.user.view.fragment

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment

/**
 * Created by reoger on 2018/5/14.
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
        val name : TextView = mRootView.findViewById(R.id.tv_user_name)
        val email : TextView = mRootView.findViewById(R.id.tv_user_email_show)
        val button : Button = mRootView.findViewById(R.id.bu_user_logout)

        button.setOnClickListener({
            toast("点击了")
        })
    }

}