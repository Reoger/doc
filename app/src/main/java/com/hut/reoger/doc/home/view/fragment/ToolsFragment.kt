package com.hut.reoger.doc.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.R

/**
 * Created by CM on 2018/2/1.
 */
class ToolsFragment : BaseFragment(){

    companion object {
        fun getInstance(): ToolsFragment {
            return Inner.fragment
        }
    }

    private object Inner{
        val fragment = ToolsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_tools,container,false)
        return v
    }
}