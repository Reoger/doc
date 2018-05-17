package com.hut.reoger.doc.home.view.fragment

import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.TextView
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.doc.view.MyDocumentActivity
import com.hut.reoger.doc.home.view.CommentActivity
import com.hut.reoger.doc.home.view.MarkDocActivity
import com.hut.reoger.doc.uploader.view.UploaderActivity
import com.hut.reoger.doc.user.view.LoginActivity

/**
 * Created by CM on 2018/2/1.
 *
 */
class DocumentFragment : BaseFragment(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_document
    }

    companion object {
        fun getInstance(): DocumentFragment {
            return Inner.documentFragment
        }
    }

    private object Inner{
        val documentFragment = DocumentFragment()
    }



    override fun initView(view: View) {
        val floatingActionButton = view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener({
            //点击事件
            if (App.instance.userInfo == null ||App.instance.passWord== null){
                openActivity(LoginActivity::class.java)
            }else{
                openActivity(UploaderActivity::class.java)
            }
        })
        view.findViewById<TextView>(R.id.doc_mark_tv).setOnClickListener({
            openActivity(MarkDocActivity::class.java)
        })

        view.findViewById<TextView>(R.id.doc_comment_tv).setOnClickListener({
            openActivity(CommentActivity::class.java)
        })

        view.findViewById<TextView>(R.id.tv_my_doc).setOnClickListener({
            openActivity(MyDocumentActivity::class.java)
        })
    }
}