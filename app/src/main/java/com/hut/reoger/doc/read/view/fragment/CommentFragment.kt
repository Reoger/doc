package com.hut.reoger.doc.read.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseDialogFragment
import com.hut.reoger.doc.read.`interface`.IDialogFragmentCallback
import kotlinx.android.synthetic.main.fragment_dialog_comment.*

/**
 * Created by reoger on 2018/4/2.
 */

class CommentFragment : BaseDialogFragment(){

    //单利模式
    companion object {
        fun getInstance(): CommentFragment {
            return Inner.commentFragment
        }
    }

    private object Inner{
        val commentFragment = CommentFragment()
    }


    private var callback :IDialogFragmentCallback ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         callback = arguments["callback"] as IDialogFragmentCallback
        val view:View = inflater.inflate(R.layout.fragment_dialog_comment,container)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        val text = view.findViewById<EditText>(R.id.comment_et)
        val btu = view.findViewById<Button>(R.id.comment_but)
        btu.setOnClickListener({
            callback?.response(text.text.toString(),comment_rating_bar.numStars)
            dismiss()
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}