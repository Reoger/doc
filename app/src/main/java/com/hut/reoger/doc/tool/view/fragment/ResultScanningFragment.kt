package com.hut.reoger.doc.tool.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment

/**
 * Created by reoger on 2018/4/22.
 */
class ResultScanningFragment :BaseFragment(){

    var content:String ?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val bundle:Bundle = arguments
        content = bundle.getString("key","")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_result_scanning
    }

    override fun initView(mRootView: View) {
       val tv = mRootView.findViewById<TextView>(R.id.tv_result_scanning)
        tv.text = content
    }

}