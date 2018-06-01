
package com.hut.reoger.doc.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.cm.mytestdemo.utils.loadingUtils.LoadDialog
import com.hut.reoger.doc.R
import com.hut.reoger.doc.R.id.layout_center


import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import com.hut.reoger.doc.utils.loadingUtils.SlidingInAndOutDialogFragment


/**
 * Created by CM on 2018/1/23.
 *
 */
abstract class BaseActivity : RxAppCompatActivity() {

    protected val TAG: String = "debug"

    private var onMenuItemClickListener: Toolbar.OnMenuItemClickListener? = null

    private var menuRes: Int = -1

    protected var dialog: Dialog?= null






    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }



    override fun setContentView(@LayoutRes layoutResID: Int) {

        if (layout_center == null && R.layout.activity_base == layoutResID) {
            super.setContentView(R.layout.activity_base)
            layout_center.removeAllViews()
        } else {
            val addView: View = LayoutInflater.from(this).inflate(layoutResID, null)

            layout_center.addView(addView, ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
            initView()
            beforeSetActionBar()
            setActionBar()
            afterSetActionBar()
        }
    }

    fun setMenu(@MenuRes menuRes: Int,onMenuItemClickListener: Toolbar.OnMenuItemClickListener){
        this.menuRes = menuRes
        this.onMenuItemClickListener = onMenuItemClickListener
    }

     fun setMenuId(@MenuRes menuRes:Int){
        this.menuRes = menuRes
    }


    private fun afterSetActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener({ finish() })
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if( menuRes!=-1 && onMenuItemClickListener!= null) {
            menuInflater?.inflate(menuRes, menu)
        }else
            return super.onCreateOptionsMenu(menu)
        return true
    }


    private fun beforeSetActionBar() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.title = ""
        toolbar.isEnabled = true

    }

    fun setActivityTitle(str: String) {
        toolbar_title.text = str
    }

    fun setActivityTitle(@StringRes textId: Int) {
        toolbar_title.text = getString(textId)
    }

    fun setLeftImg(@DrawableRes imgId : Int){
        toolbar.setNavigationIcon(imgId)
    }


    protected fun setRight(text:String, listener:View.OnClickListener) {
        am_right_tv.text = text
        am_right_tv.setOnClickListener(listener)
    }

    fun setRightText(text: String){
        am_right_tv.text = text
    }



    abstract fun setActionBar()

    abstract fun initView()


    /**
     * 快捷通知
     */
    fun toast(str: String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }

    /**
     * 启动另外一个activity
     * @targetActivityClass 启动的目标activity
     * @bundle 传递的数据
     */
    @JvmOverloads fun openActivity(targetActivityClass: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(this, targetActivityClass)
        bundle?.let { intent.putExtras(bundle) }
        startActivity(intent)
    }

    fun openActivity(targetActivityClass: Class<*>, targetName: String, targetMessage: String?) {
        val intent = Intent(this, targetActivityClass)
        targetMessage?.let { intent.putExtra(targetName, targetMessage) }
        startActivity(intent)
    }
    //activity跳转并关闭当前页面
    fun openActivityAndCloseThis(targetActivityClass: Class<*>, targetName: String, targetMessage: String) {
        openActivity(targetActivityClass, targetName, targetMessage)
        this.finish()
    }

    fun openActivityAndCloseThis(targetActivityClass: Class<*>) {
        openActivity(targetActivityClass)
        this.finish()
    }

    fun openActivityAndCloseThis(targetActivityClass: Class<*>, bundle: Bundle) {
        openActivity(targetActivityClass, bundle)
        this.finish()
    }



    //弹出原生交互界面
    fun AffirmDialogShow(title: String, content: String) {
        AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton("取消") { dialogInterface, _ -> dialogInterface.dismiss() }
                .setPositiveButton("确认") { dialogInterface, _ ->
                    okOperation()//点击确认操作
                    dialogInterface.dismiss()
                }.show()
    }


    /**
     * 确定按钮的逻辑
     */
    protected open fun okOperation() {}

    fun startLoad(){
//        dialog = Dialog(this,R.style.progress_dialog)
//        dialog?.setContentView(R.layout.dialog)
//        dialog?.setCancelable(false)
//        dialog!!.window.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog?.setCanceledOnTouchOutside(false)
//        dialog?.show()
        LoadDialog.show(this)
    }


    fun stopLoad(){
//        dialog?.let {
//            if(dialog!!.isShowing){
//                dialog?.dismiss()
//            }
//        }
        LoadDialog.cancel()
    }

    fun log(str: String){
        Log.d(TAG,str)
    }


    fun  showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        //      ft.setCustomAnimations(R.anim.fragment_slide_left_enter, 0);

        // Create and show the dialog.
        val newFragment = SlidingInAndOutDialogFragment()
        newFragment.show(ft, "dialog")
    }






}



