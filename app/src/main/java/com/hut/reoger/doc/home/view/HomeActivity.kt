package com.hut.reoger.doc.home.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.view.fragment.HomeFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by CM on 2018/1/25.
 *
 */

class HomeActivity : RxAppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("debug","这里不应该是可以那个啥，取消的吗")
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
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        initFragment()
        navigation.setOnNavigationItemSelectedListener({
            when(it.itemId){
                R.id.navigation_home->{


                }
                R.id.navigation_dashboard->{

                }
                R.id.navigation_notifications->{

                }
                else->{

                }
            }
                false
        })
    }

    private fun initFragment() {
        val beginTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment()
        beginTransaction.replace(R.id.frame_content,homeFragment)
        beginTransaction.addToBackStack(null)
        beginTransaction.commit()
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }






}