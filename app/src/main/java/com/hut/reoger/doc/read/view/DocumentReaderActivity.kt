package com.hut.reoger.doc.read.view

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.R.id.mSuperFileView
import com.hut.reoger.doc.read.`interface`.ICallBack
import com.hut.reoger.doc.read.presenter.DocumentReadPresenterImple
import com.hut.reoger.doc.read.view.fragment.CommentFragment
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import java.io.File
import java.io.Serializable

/**
 * Created by reoger on 2018/3/2.
 * 阅读界面
 *
 */


class DocumentReaderActivity : BaseActivity(), IReadView {

    override fun updateProgress(progress: Int) {

        if (progress >= 99)
            stopLoad()
    }

    private var mSuperFileView: SuperFileView2? = null

    companion object {
        const val READ_ONLINE = "read_activity"
    }


    private var mReadPresenter: DocumentReadPresenterImple? = null

    private var filePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_doc)

    }


    override fun setActionBar() {
        setActivityTitle("在线阅读界面")
    }

    override fun initView() {
        mReadPresenter = DocumentReadPresenterImple(this, this)
        filePath = intent.getStringExtra(READ_ONLINE)
        if (null == filePath || "" == filePath)
            filePath = "http://www.hrssgz.gov.cn/bgxz/sydwrybgxz/201101/P020110110748901718161.doc"
        init()
    }


    private fun init() {
        mSuperFileView = findViewById(R.id.mSuperFileView)
        mSuperFileView!!.setOnGetFilePathListener(object : SuperFileView2.OnGetFilePathListener {
            override fun onGetFilePath(mSuperFileView2: SuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2)
            }
        })

        mSuperFileView!!.show()


        setMenu(R.menu.menu_doc, Toolbar.OnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.menu_comment -> {

                }
                R.id.menu_mark -> {
                    toast("点击了标记")
                }
                R.id.menu_about -> {
                    val dilog = CommentFragment.getInstance()

                    val bundle = Bundle()
                    bundle.putSerializable("callback", object :ICallBack, Serializable {
                        override fun response(message: String) {
                           toast(message)
                        }
                    })
                    dilog.arguments = bundle
                    dilog.show(fragmentManager, "tag")
                }
                R.id.menu_setting -> {

                }
                R.id.menu_share -> {

                }
                R.id.menu_show_marks -> {

                }
                else -> {

                }

            }
            true
        })
    }


    private fun getFilePathAndShowFile(mSuperFileView2: SuperFileView2) {

        if (filePath!!.contains("http")) {//网络地址要先下载
            startLoad()
            mReadPresenter?.downLoadFromNet(filePath!!, mSuperFileView2)
        } else {
            mSuperFileView2.displayFile(File(filePath))
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        TLog.d("FileDisplayActivity-->onDestroy")
        if (mSuperFileView != null) {
            mSuperFileView!!.onStopDisplay()
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_doc,menu)
//        super.onCreateOptionsMenu(menu)
//        return true
//    }


//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        LogUtils.d("uuu")
//        when(item?.itemId){
//            R.id.menu_comment->{
//
//            }
//            R.id.menu_mark->{
//                toast("点击了标记")
//            }
//            R.id.menu_about->{
//                CommentFragment.getInstance().show(fragmentManager,"dialogFragment Test")
//            }
//            R.id.menu_setting->{
//
//            }
//            R.id.menu_share->{
//
//            }
//            R.id.menu_show_marks->{
//
//            }
//            else ->{
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


}
