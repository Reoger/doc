package com.hut.reoger.doc.read.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseDiaologFragment
import com.hut.reoger.doc.read.`interface`.ICallBack
import com.hut.reoger.doc.utils.log.LogUtils

/**
 * Created by reoger on 2018/4/2.
 */
class CommentFragment : BaseDiaologFragment(){

    //单利模式
    companion object {
        fun getInstance(): CommentFragment {
            return Inner.commentFragment
        }
    }

    private object Inner{
        val commentFragment = CommentFragment()
    }


    private var callback :ICallBack ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         callback = arguments["callback"] as ICallBack
        val view:View = inflater.inflate(R.layout.fragment_dialog_comment,container)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        val text = view.findViewById<EditText>(R.id.id_txt_username)
        val btu = view.findViewById<Button>(R.id.comment_but)
        btu.setOnClickListener({
            LogUtils.d("测试${text.text}")
            callback?.response(text.text.toString())
            dismiss()
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}