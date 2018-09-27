package com.jhj.jshare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.jshare.jshare.JShareDialog
import com.jhj.jshare.jshare.bean.LinkShareBuilder
import com.jhj.jshare.jshare.bean.TextShareBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://www.eqid.top:8009/image/workCircle/20180926/13926590001/2018092616372553700.jpg"
        btn_text.onClick {
            val textShare = TextShareBuilder()
            textShare.setText("测试")
                    .setTitle("测试分享")
            JShareDialog(this@MainActivity, textShare).show()

        }

        btn_link.onClick {
            val linkShare = LinkShareBuilder()
            val b = "/storage/emulated/0/Download/08-27-33-image.jpg"
            linkShare.setText("测试")
                    .setTitle("测试分享")
                    //.setImagePath(b)
                    .setImageUrl(url)
                    .setUrl("http://baidu.com")
            JShareDialog(this@MainActivity, linkShare).show()
        }

    }
}
