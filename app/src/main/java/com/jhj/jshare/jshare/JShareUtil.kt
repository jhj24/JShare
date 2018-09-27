package com.jhj.jshare.jshare

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import cn.jiguang.share.android.api.JShareInterface
import cn.jiguang.share.android.api.PlatActionListener
import cn.jiguang.share.android.api.Platform
import cn.jiguang.share.android.api.ShareParams
import cn.jiguang.share.qqmodel.QQ
import cn.jiguang.share.qqmodel.QZone
import cn.jiguang.share.wechat.Wechat
import cn.jiguang.share.wechat.WechatFavorite
import cn.jiguang.share.wechat.WechatMoments
import cn.jiguang.share.weibo.SinaWeibo
import com.jhj.jshare.MainActivity
import com.jhj.jshare.MyApplication
import com.jhj.jshare.R
import com.jhj.jshare.jshare.bean.ImgShareBuilder
import com.jhj.jshare.jshare.bean.LinkShareBuilder
import com.jhj.jshare.jshare.bean.TextShareBuilder
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.toast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 *
 * Created by jhj on 18-5-25.
 */

class JShareUtil() {

    private lateinit var imgShareBuilder: ImgShareBuilder
    private lateinit var textShareBuilder: TextShareBuilder
    private lateinit var linkShareBuilder: LinkShareBuilder
    private var type: ShareType? = null
    private var mActivity: Activity? = null


    enum class ShareType {
        TEXT,
        IMG,
        LINK,
        /*  FILE,
          VIDEO*/
    }


    constructor(imgShareBuilder: ImgShareBuilder, mActivity: Activity) : this() {
        this.mActivity = mActivity
        this.imgShareBuilder = imgShareBuilder
        type = ShareType.IMG
    }

    constructor(textShareBuilder: TextShareBuilder, mActivity: Activity) : this() {
        this.mActivity = mActivity
        this.textShareBuilder = textShareBuilder
        type = ShareType.TEXT
    }

    constructor(linkShareBuilder: LinkShareBuilder, mActivity: Activity) : this() {
        this.mActivity = mActivity
        this.linkShareBuilder = linkShareBuilder
        type = ShareType.LINK
    }

    fun share2EQD() {
        //todo
        when (type) {
            JShareUtil.ShareType.IMG -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(imgShareBuilder.imagePath)))
                intent.component = ComponentName("com.eqdd.yiqidian", "com.eqdd.yiqidian.ui.share.ShareEnterActivity")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mActivity?.startActivity(intent)
            }
            JShareUtil.ShareType.TEXT -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain") // 纯文本
                intent.putExtra(Intent.EXTRA_SUBJECT, textShareBuilder.title)
                intent.putExtra(Intent.EXTRA_TEXT, textShareBuilder.text)
                intent.setComponent(ComponentName("com.eqdd.yiqidian", "com.eqdd.yiqidian.ui.share.ShareEnterActivity"))
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mActivity?.startActivity(intent)
            }
        }
    }

    fun share2WorkCircle() {
        when (type) {
            JShareUtil.ShareType.IMG -> {
            }
            JShareUtil.ShareType.TEXT -> {
            }
            JShareUtil.ShareType.LINK -> {
                //todo 根据需求写
                val intent = Intent(mActivity, MainActivity::class.java)
                intent.putExtra("title", linkShareBuilder.title)
                intent.putExtra("image", linkShareBuilder.imageUrl)
                intent.putExtra("source", linkShareBuilder.source)
                intent.putExtra("url", linkShareBuilder.url)
                intent.putExtra("id", linkShareBuilder.id)
                mActivity?.startActivity(intent)
            }
        }
    }

    fun share2Collection() {
        //todo 根据需求写,如果是添加到收藏,大概是将参数提交到网络请求
        when (type) {
            JShareUtil.ShareType.IMG -> CollectionUtil.chooseImg(imgShareBuilder.imageUrl, imgShareBuilder.source, imgShareBuilder.sourceOwner)
            JShareUtil.ShareType.TEXT -> CollectionUtil.chooseText(textShareBuilder.title, textShareBuilder.text, textShareBuilder.source, textShareBuilder.sourceOwner)
            JShareUtil.ShareType.LINK -> CollectionUtil.chooseLink(linkShareBuilder.title, linkShareBuilder.url, linkShareBuilder.source,
                    linkShareBuilder.sourceOwner, linkShareBuilder.id)
        }
    }

    fun share2WeXin(platform: String) {
        val shareParams = ShareParams()
        when (type) {
            JShareUtil.ShareType.TEXT -> {
                shareParams.shareType = Platform.SHARE_TEXT
                shareParams.text = textShareBuilder.text//必须
                share(platform, shareParams)
            }
            JShareUtil.ShareType.IMG -> {
                shareParams.shareType = Platform.SHARE_IMAGE
                if (!TextUtils.isEmpty(imgShareBuilder.imagePath)) {
                    shareParams.imagePath = imgShareBuilder.imagePath
                    share(platform, shareParams)
                } else {
                    shareBitmap(imgShareBuilder.imageUrl, platform, shareParams)
                }
            }
            JShareUtil.ShareType.LINK -> {
                shareParams.title = linkShareBuilder.title
                shareParams.text = linkShareBuilder.text
                shareParams.shareType = Platform.SHARE_WEBPAGE
                shareParams.url = linkShareBuilder.url//必须
                if (!TextUtils.isEmpty(linkShareBuilder.imagePath)) {
                    shareParams.imagePath = linkShareBuilder.imagePath
                    share(platform, shareParams)
                } else {
                    shareBitmap(linkShareBuilder.imageUrl, platform, shareParams)
                }
            }
        }


    }

    fun share2Q(platform: String) {
        val shareParams = ShareParams()
        when (type) {
            JShareUtil.ShareType.TEXT -> {
                shareParams.shareType = Platform.SHARE_TEXT
                shareParams.text = textShareBuilder.text
            }
            JShareUtil.ShareType.IMG -> {
                shareParams.shareType = Platform.SHARE_IMAGE
                if (platform == QQ.Name) {
                    shareParams.imagePath = imgShareBuilder.imagePath
                } else if (platform == QZone.Name) {
                    if (TextUtils.isEmpty(imgShareBuilder.imageUrl)) {
                        shareParams.imagePath = imgShareBuilder.imagePath
                    } else {
                        shareParams.imageUrl = imgShareBuilder.imageUrl
                    }
                }

            }
            JShareUtil.ShareType.LINK -> {
                if (platform == QQ.Name) {
                    shareParams.title = subString(linkShareBuilder.title, 30)
                    shareParams.text = subString(linkShareBuilder.text, 40)
                }
                if (platform == QZone.Name) {
                    shareParams.title = subString(linkShareBuilder.title, 200)
                    shareParams.text = subString(linkShareBuilder.text, 600)
                }
                shareParams.shareType = Platform.SHARE_WEBPAGE
                shareParams.url = linkShareBuilder.url//必须
                if (TextUtils.isEmpty(linkShareBuilder.imageUrl)) {
                    shareParams.imagePath = linkShareBuilder.imagePath
                } else {
                    shareParams.imageUrl = linkShareBuilder.imageUrl
                }
            }

        }
        share(platform, shareParams)
    }


    fun share2Sina(platform: String) {
        val shareParams = ShareParams()
        when (type) {
            JShareUtil.ShareType.TEXT -> {
                shareParams.text = subString(textShareBuilder.text, 1999)
                shareParams.shareType = Platform.SHARE_TEXT
                shareParams.imageData = BitmapFactory.decodeResource(mActivity?.resources, R.mipmap.ic_share_weibo)
                share(SinaWeibo.Name, shareParams)
            }
            JShareUtil.ShareType.IMG -> {
                shareParams.text = subString(imgShareBuilder.text, 1999)
                shareParams.shareType = Platform.SHARE_IMAGE
                if (!TextUtils.isEmpty(imgShareBuilder.imagePath)) {
                    shareParams.imagePath = imgShareBuilder.imagePath
                    share(SinaWeibo.Name, shareParams)
                } else {
                    shareBitmap(imgShareBuilder.imageUrl, SinaWeibo.Name, shareParams)
                }
            }
            JShareUtil.ShareType.LINK -> {
                shareParams.text = subString(linkShareBuilder.text, 1999)
                shareParams.shareType = Platform.SHARE_WEBPAGE
                shareParams.url = linkShareBuilder.url
                if (!TextUtils.isEmpty(linkShareBuilder.imagePath)) {
                    shareParams.imagePath = linkShareBuilder.imagePath
                    share(platform, shareParams)
                } else {
                    shareBitmap(linkShareBuilder.imageUrl, platform, shareParams)
                }
            }
        }
    }

    fun share2Explorer() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkShareBuilder.url))
        mActivity?.startActivity(intent)
    }


    private fun subString(string: String?, length: Int): String? {
        return if (string != null && string.length > length) {
            string.substring(0, length)
        } else {
            string
        }

    }


    fun share(platForm: String, shareParams: ShareParams) {

        JShareInterface.share(platForm, shareParams, object : PlatActionListener {
            override fun onComplete(platform: Platform, i: Int, hashMap: HashMap<String, Any>) {
                println("分享成功")
            }

            override fun onError(platform: Platform, i: Int, i1: Int, throwable: Throwable) {
                println("分享失败" + throwable.toString())
                try {
                    if (platform.name == QQ.Name || platform.name == QZone.Name) {
                        mActivity?.toast("请安装QQ")
                    } else if (platform.name == Wechat.Name || platform.name == WechatFavorite.Name
                            || platform.name == WechatMoments.Name) {
                        mActivity?.toast("请安装微信")
                    } else if (platform.name == SinaWeibo.Name) {
                        mActivity?.toast("请安装微博")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onCancel(platform: Platform, i: Int) {
                println("分享取消")
            }
        })
    }


    fun shareBitmap(string: String?, platform: String, shareParams: ShareParams) {
        doAsyncResult {
            var bitmap: Bitmap
            try {
                val url = URL(string)
                val conn = url.openConnection() as HttpsURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 10 * 1000
                val inStream = conn.inputStream//通过输入流获取图片数据
                bitmap = BitmapFactory.decodeStream(inStream)
            } catch (e: Exception) {
                bitmap = BitmapFactory.decodeResource(MyApplication.instance.resources, R.mipmap.ic_launcher)
            }
            bitmap
            //compressBitmap(bitmap)
        }.get().let {
            shareParams.imageData = it
            share(platform, shareParams)
        }
    }


    /**
     * 当需要限制bitmap大小时,将图片压缩到指定大小之下
     *
     * @param b 原bitmap
     * @return 目标bitmap
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun compressBitmap(b: Bitmap?): Bitmap? {
        var bitmap = b
        val topLimit = 30 * 1024
        if (bitmap != null) {
            val baos = ByteArrayOutputStream()
            var options = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
            Log.w("xxx", baos.toByteArray().size.toString() + "")
            while (baos.toByteArray().size > topLimit) {
                baos.reset()
                options -= 5
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
                if (options == 0) {
                    break
                }
            }
            val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成
            isBm.close()
        }
        return bitmap
    }
}