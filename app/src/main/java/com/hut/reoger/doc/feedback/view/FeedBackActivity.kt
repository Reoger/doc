package com.hut.reoger.doc.feedback.view

import android.os.Bundle
import android.text.TextUtils
import com.example.cm.mytestdemo.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.feedback.presenter.FeedBackPresenterImpl
import com.hut.reoger.doc.utils.log.TLog
import kotlinx.android.synthetic.main.activity_feedback.*

/**
 * Created by reoger on 2018/3/18.
 */

class FeedBackActivity : BaseActivity(),IFeedBackView{
    override fun feedBackSuccessful() {
        toast("反馈成功~，非常感谢你的反馈")
        finish()
    }

    override fun feedBackFail(str:String) {
        toast("反馈失败，请稍后再试$str")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
    }

    override fun setActionBar() {
        setActivityTitle("反馈")
    }

    override fun initView() {
        val mFeedBackPresenterImpl = FeedBackPresenterImpl(this,this)
        btu_feedback.setOnClickListener {
            TLog.d("打印输出"+et_feedback.text)
            if (TextUtils.isEmpty(et_feedback.text)||TextUtils.isEmpty(ed_links.text)){
                toast("请将信息填写完整~")
            }else{
                mFeedBackPresenterImpl.doFeedBack(et_feedback.text.toString(),ed_links.text.toString())
            }

        }
    }

}