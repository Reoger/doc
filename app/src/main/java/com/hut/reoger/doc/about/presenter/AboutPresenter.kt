package com.hut.reoger.doc.about.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hut.reoger.doc.about.view.IView

/**
 * Created by reoger on 2018/3/31.
 */
class AboutPresenter(var context:Context,var mIView: IView) : IPresenter{

    override fun doOpenGithub(url: String) {
        if (true){
            val uri:Uri = Uri.parse(url)
            context.startActivity(Intent(Intent.ACTION_VIEW,uri))
        }
    }

}