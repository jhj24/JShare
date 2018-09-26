package com.jhj.jshare.jshare

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import cn.jiguang.share.android.api.ShareParams
import com.jhj.jshare.MyApplication
import com.jhj.jshare.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 *  根据图片url获取bitmap
 * Created by jhj on 18-5-25.
 */
class ImageAsync(private val platform: String, private val shareParams: ShareParams) : AsyncTask<String, Bitmap, Bitmap>() {

    override fun doInBackground(vararg params: String): Bitmap? {
        var bitmap: Bitmap?
        try {
            val strUrl = params[0]
            val url = URL(strUrl)
            val conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 10 * 1000
            val inStream = conn.inputStream//通过输入流获取图片数据
            bitmap = BitmapFactory.decodeStream(inStream)
        } catch (e: Exception) {
            bitmap = BitmapFactory.decodeResource(MyApplication.instance.resources, R.mipmap.ic_launcher)
        }
        //bitmap = compressBitmap(bitmap);
        return bitmap
    }

    override fun onPostExecute(bitmap: Bitmap) {
        shareParams.imageData = bitmap
        JShareUtil.share(platform, shareParams)
    }

    /**
     * 当需要限制bitmap大小时
     * 将图片压缩到指定大小之下
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
            }
            val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成
            isBm.close()
        }
        return bitmap
    }
}
