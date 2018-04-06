package com.hut.reoger.doc.read.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cm.mytestdemo.base.BaseFragment
import com.hut.reoger.doc.R

/**
 * Created by reoger on 2018/4/6.
 */
class CommentList : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_comment,container,false)

        return v
    }
}