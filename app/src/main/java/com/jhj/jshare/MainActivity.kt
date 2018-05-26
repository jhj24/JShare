package com.jhj.jshare

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jhj.jshare.jshare.JShareDialog
import com.jhj.jshare.jshare.bean.TextShareBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        share.onClick {
            val textShare = TextShareBuilder()
            textShare.setText("测试")
                    .setTitle("测试分享")
            JShareDialog(this@MainActivity, textShare)

        }


    }
}
