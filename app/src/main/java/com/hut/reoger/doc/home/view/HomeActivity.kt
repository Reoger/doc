package com.hut.reoger.doc.home.view

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hujiang.permissiondispatcher.NeedPermission
import com.hut.reoger.doc.R
import com.hut.reoger.doc.about.view.AboutActivity
import com.hut.reoger.doc.feedback.view.FeedBackActivity
import com.hut.reoger.doc.read.view.DocumentReaderActivity
import com.hut.reoger.doc.settings.view.SettingsActivity
import com.hut.reoger.doc.share.ShareUtils
import com.hut.reoger.doc.utils.log.LogUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.hut.reoger.doc.home.view.fragment.DocumentFragment
import com.hut.reoger.doc.home.view.fragment.HomeFragment
import com.hut.reoger.doc.home.view.fragment.ToolsFragment
import com.hut.reoger.doc.utils.aop.log.AspectLogUtils
import com.hut.reoger.doc.utils.aop.networkState.AspectNetworkAnnotation
import com.hut.reoger.doc.utils.aop.permission.AspectPermissionAnnotation


/**
 * Created by CM on 2018/1/25.
 *
 */

class HomeActivity : RxAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    var mCurrentIndex: Int = 0


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_person -> {

//                App.instance.showDialog(this)
                startActivity(Intent(HomeActivity@ this, DocumentReaderActivity::class.java))
            }
            R.id.nav_history -> {
                //历史
//                val shareUtils = ShareUtils(this)
//                shareUtils.shareImage("test")
                testAop()
            }
            R.id.nav_about -> {
                //这里打算弄一个沉浸式的界面
                //加上我的个人信息介绍
                startActivity(Intent(HomeActivity@ this, AboutActivity::class.java))
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_share -> {
                val shareUtils = ShareUtils(this)
                shareUtils.shareText("这里是标题")
            }
            R.id.nav_feedfack -> {
                startActivity(Intent(this, FeedBackActivity::class.java))
            }
            else -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    @AspectLogUtils.AspectLog(value = "这里是在测试 kotlin")
//    @JustTest.Fancy()
//    @AspectLogTest.AspectLog(value = "hello 测试")
    fun testAop() {
        LogUtils.d("这里是在测试")
        checkPermisson()
        testAop2()
    }


    @AspectPermissionAnnotation(value = Manifest.permission.CAMERA)
    fun checkPermisson() {
        LogUtils.d("这里在检查权限")
    }

    @AspectNetworkAnnotation()
    fun testAop2() {
        LogUtils.d("测试,检查网络")
        chePer()
//        startActivity(Intent(HomeActivity@ this, testActivity::class.java))
    }



//    @AspectPermissionAnnotation(value = Manifest.permission.CAMERA)
@NeedPermission(permissions = arrayOf(Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_CONTACTS))
    private fun chePer() {
        LogUtils.d("测试 在kotlin中")
        Toast.makeText(this, "检查权限", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //    @AspectLogTest.AspectLog(value = "创建activity时的log")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle!!)

        choiceFragment(true, 0)
        navigation.setOnNavigationItemSelectedListener({
            when (it.itemId) {
                R.id.navigation_home -> {
                    if (mCurrentIndex != 0)
                        choiceFragment(false, 0)
                    mCurrentIndex = 0
                }
                R.id.navigation_document -> {
                    if (mCurrentIndex != 1)
                        choiceFragment(false, 1)
                    mCurrentIndex = 1
                }
                R.id.navigation_tools -> {
                    if (mCurrentIndex != 2)
                        choiceFragment(false, 2)
                    mCurrentIndex = 2
                }
                else -> {

                }
            }
            true
        })
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        super.onPostCreate(savedInstanceState)
        nav_view.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_setting -> {
                LogUtils.d("点击了设置")
            }
            else -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }


    private fun choiceFragment(init: Boolean, choice: Int) {

        val beginTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment.getInstance()
        val toolFragment = ToolsFragment.getInstance()
        val documentFragment = DocumentFragment.getInstance()
        if (init) {
            beginTransaction.add(R.id.frame_content, homeFragment)
            beginTransaction.add(R.id.frame_content, toolFragment)
            beginTransaction.add(R.id.frame_content, documentFragment)
            beginTransaction.show(homeFragment)
            beginTransaction.hide(toolFragment)
            beginTransaction.hide(documentFragment)
        } else {
            when (choice) {
                0 -> {
                    beginTransaction.show(homeFragment)
                    beginTransaction.hide(toolFragment)
                    beginTransaction.hide(documentFragment)
                }
                1 -> {
                    beginTransaction.show(documentFragment)
                    beginTransaction.hide(homeFragment)
                    beginTransaction.hide(toolFragment)
                }
                2 -> {
                    beginTransaction.show(toolFragment)
                    beginTransaction.hide(homeFragment)
                    beginTransaction.hide(documentFragment)
                }
            }


        }
        beginTransaction.commit()

    }
}