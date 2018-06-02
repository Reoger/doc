package com.hut.reoger.doc.read.view

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.hut.reoger.doc.App
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.read.`interface`.IDialogFragmentCallback
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.read.presenter.DocumentReadPresenterImple
import com.hut.reoger.doc.read.presenter.IDocumentReadPresenter
import com.hut.reoger.doc.read.view.fragment.CommentFragment
import com.hut.reoger.doc.read.view.fragment.CommentListFragment
import com.hut.reoger.doc.user.view.LoginActivity
import com.hut.reoger.doc.utils.aop.networkState.AspectNetworkAnnotation
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import kotlinx.android.synthetic.main.layout_doc.*
import java.io.File
import java.io.Serializable

/**
 * Created by reoger on 2018/3/2.
 * 阅读界面
 * kotlin-java
 *
 */


class DocumentReaderActivity : BaseActivity(), IReadView {


    private var mSuperFileView: SuperFileView2? = null


    companion object {

        const val COMMENT_FRAGMENT = "comment_fragment"
        const val COMMENT_LIST_DATA = "data_for_comment_list"

        const val DOC_ID = "doc_id"
        const val DOC_URL = "doc_url"

    }

    private var mReadPresenter: IDocumentReadPresenter? = null


    private var filePath: String? = null
    private var docId: String? = null

    private var commentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_doc)
        commentFragment = CommentListFragment.getInstance()
    }

    override fun setActionBar() {
        setActivityTitle(getString(R.string.read_activity))

    }

    /**
     * 删除评论
     */
    fun deleteCommentByDoc(doc_id: Int, pos: Int) {
        mReadPresenter?.deleteComment(doc_id)
        LogUtils.d("这里被fragment调用了$doc_id")
        (commentFragment as CommentListFragment).deleteCommentSuccessful(pos)
    }


    override fun deleteCommentSuccessful() {
        toast(getString(R.string.comment_success))

    }

    override fun deleteCommentFail(error: String) {
        toast(getString(R.string.comment_fail))
    }


    override fun loadCommentSuccessful(data: CommentsByDoc?) {
        if (data == null || data.data?.isEmpty() == true) {
            toast(getString(R.string.comment_no_body))
        } else {
            if (commentFragment?.isAdded == true) {
                comment_list.visibility = View.VISIBLE
                val transaction = fragmentManager.beginTransaction()
                transaction.show(commentFragment).commit()
            } else {
                comment_list.visibility = View.VISIBLE
                val transaction = fragmentManager.beginTransaction()
                val bundle = Bundle()
                bundle.putSerializable(COMMENT_LIST_DATA, data)
                commentFragment?.arguments = bundle
                LogUtils.d("其实有想要加载fragment")
                transaction.add(R.id.comment_list, commentFragment).commit()
            }
        }
    }

    override fun commentFail(error: String) {
        toast(getString(R.string.comment_fail)+error)
    }

    override fun commentSuccessful(data: ServiceReply) {
        toast(getString(R.string.comment_success))
    }

    override fun updateProgress(progress: Int) {
        if (progress >= 99)
            stopLoad()
    }

    override fun initView() {
        mReadPresenter = DocumentReadPresenterImple(this, this)

        val bundle: Bundle = intent.extras
        if (bundle !=null){
            LogUtils.d("bumdle !=null and = $bundle")
        }
        filePath = bundle.getString(DOC_URL, "")
        docId = bundle.getString(DOC_ID, "")

        LogUtils.d("filePath -> $filePath ,docId -> $docId")
        if (null == filePath || "" == filePath)
            toast(getString(R.string.can_not_find_file))
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
                    if(App.instance.userInfo != null){
                        doComment()
                    }else{
                        toast(getString(R.string.user_not_login))
                        openActivity(LoginActivity::class.java)
                    }

                }
                R.id.menu_mark -> {
                    docId?.apply {
                        if (mReadPresenter?.isCurrentDocMarked(this) == true) {
                            toast("当前的文章没有已经被,现在取消标记")
                            mReadPresenter?.cancelDocMarked(this)
                        } else {
                            toast("当前的文章没有被标记,现在标记了")
                            mReadPresenter?.markDoc(doc_name = "文章标题", doc_id = this, user_id = App.instance.userInfo!!.userId)
                        }
                    }

                }
                R.id.menu_about -> {
                    testAopNetWork()
                }
                R.id.menu_setting -> {

                }
                R.id.menu_share -> {

                }
                R.id.menu_show_comment -> {
                   if (App.instance.userInfo == null){
                       toast(getString(R.string.user_not_login))
                       openActivity(LoginActivity::class.java)
                   }else{
                       doShowComment()
                   }

                }
                else -> {

                }

            }
            true
        })
    }

    private fun doShowComment() {
        if (commentFragment?.isAdded == true) {
            LogUtils.d("fragment已经添加，")
            comment_list.visibility = View.VISIBLE
            val transaction = fragmentManager.beginTransaction()
            transaction.show(commentFragment).commit()
        } else {
            docId?.let { mReadPresenter?.loadComments(it) }

        }
    }

    private fun doComment() {
        val diloag = CommentFragment.getInstance()
        val bundle = Bundle()
        bundle.putSerializable("callback", object : IDialogFragmentCallback, Serializable {
            override fun response(message: String, score: Int) {
                toast(message)
                //这里可以进行那个网络评论的实际操作
                docId?.let {
                    mReadPresenter?.doComment(message, "admin", it, score)
                }
            }
        })
        diloag.arguments = bundle
        diloag.show(fragmentManager, "tag")
    }

    @AspectNetworkAnnotation()
    fun testAopNetWork() {
        toast("测试网络状态")
    }

    override fun onRestart() {
        super.onRestart()
        comment_list.visibility = View.GONE
        LogUtils.d("onRestart")
    }

    override fun onResume() {
        super.onResume()
//        comment_list.visibility = View.GONE
        LogUtils.d("onResume")
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


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        commentFragment = fragmentManager.getFragment(savedInstanceState, COMMENT_FRAGMENT) as CommentListFragment

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (commentFragment?.isAdded == true) {
            fragmentManager.putFragment(outState, COMMENT_FRAGMENT, commentFragment)
        }//避免出现异常
    }

    override fun onBackPressed() {
        if (commentFragment?.isAdded == true) {
            val transaction = fragmentManager.beginTransaction()
            transaction.hide(commentFragment).commit()
            comment_list.visibility = View.GONE
        } else {
            super.onBackPressed()
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
