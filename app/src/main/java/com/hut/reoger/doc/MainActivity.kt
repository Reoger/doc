package com.hut.reoger.doc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hut.reoger.doc.home.view.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this,HomeActivity::class.java))
    }
}
