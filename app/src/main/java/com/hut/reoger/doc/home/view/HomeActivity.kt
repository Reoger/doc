package com.hut.reoger.doc.home.view

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.hut.reoger.doc.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by CM on 2018/1/25.
 *
 */

/**
 * Created by CM on 2018/1/25.
 *
 */

class HomeActivity : RxAppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var  actionBarDrawerToggle: ActionBarDrawerToggle ?= null

    var mCurrentIndex:Int = 0


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("debug","这撒大声地的吗")
        when(item.itemId){
            R.id.nav_camera->{

            }
            R.id.nav_gallery->{

            }
            R.id.nav_slideshow->{

            }
            else ->{

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle!!)

        choiceFragment(true,0)
        navigation.setOnNavigationItemSelectedListener({
            when(it.itemId){
                R.id.navigation_home->{
                    if(mCurrentIndex != 0)
                        choiceFragment(false,0)
                    mCurrentIndex = 0
                }
                R.id.navigation_document ->{
                    if(mCurrentIndex != 1)
                        choiceFragment(false,1)
                    mCurrentIndex = 1
                }
                R.id.navigation_tools ->{
                    if(mCurrentIndex != 2)
                        choiceFragment(false,2)
                    mCurrentIndex = 2
                }
                else->{

                }
            }
            true
        })
    }



    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
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
        when(item?.itemId){
            R.id.action_setting->{

            }else ->{
            return true
        }
        }
        return super.onOptionsItemSelected(item)

    }


    private fun choiceFragment(init:Boolean, choice: Int){

        val beginTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment.getInstance()
        val toolFragment = ToolsFragment.getInstance()
        val documentFragment = DocumentFragment.getInstance()
        if(init){
            beginTransaction.add(R.id.frame_content,homeFragment)
            beginTransaction.add(R.id.frame_content,toolFragment)
            beginTransaction.add(R.id.frame_content,documentFragment)
            beginTransaction.show(homeFragment)
            beginTransaction.hide(toolFragment)
            beginTransaction.hide(documentFragment)
        }else{
            when(choice){
                0->{
                    beginTransaction.show(homeFragment)
                    beginTransaction.hide(toolFragment)
                    beginTransaction.hide(documentFragment)
                }
                1->{
                    beginTransaction.show(documentFragment)
                    beginTransaction.hide(homeFragment)
                    beginTransaction.hide(toolFragment)
                }
                2->{
                    beginTransaction.show(toolFragment)
                    beginTransaction.hide(homeFragment)
                    beginTransaction.hide(documentFragment)
                }
            }


        }
        beginTransaction.commit()

    }
}