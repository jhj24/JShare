package com.jhj.jshare.jshare;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jhj.jshare.MainActivity;
import com.jhj.jshare.MyApplication;
import com.jhj.jshare.R;
import com.jhj.jshare.jshare.bean.ImgShareBuilder;
import com.jhj.jshare.jshare.bean.LinkShareBuilder;
import com.jhj.jshare.jshare.bean.TextShareBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;

/**
 * @author吕志豪 .
 * @date 18-4-14  上午9:39.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class JShareUtil {

    private ShareParams shareParams;
    private ImgShareBuilder imgShareBuilder;
    private TextShareBuilder textShareBuilder;
    private LinkShareBuilder linkShareBuilder;
    private ShareType type;
    private Activity baseActivity;


    public JShareUtil(ImgShareBuilder imgShareBuilder, Activity baseActivity) {
        this.baseActivity = baseActivity;
        this.imgShareBuilder = imgShareBuilder;
        type = ShareType.IMG;
    }

    public JShareUtil(TextShareBuilder textShareBuilder, Activity baseActivity) {
        this.baseActivity = baseActivity;
        this.textShareBuilder = textShareBuilder;
        type = ShareType.TEXT;
    }

    public JShareUtil(LinkShareBuilder linkShareBuilder, Activity baseActivity) {
        this.baseActivity = baseActivity;
        this.linkShareBuilder = linkShareBuilder;
        type = ShareType.LINK;
    }

    public void share2EQD() {
        //todo
        switch (type) {
            case IMG:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imgShareBuilder.getImagePath())));
                intent.setComponent(new ComponentName("com.eqdd.yiqidian", "com.eqdd.yiqidian.ui.share.ShareEnterActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                baseActivity.startActivity(intent);
                break;
            case TEXT:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain"); // 纯文本
                intent.putExtra(Intent.EXTRA_SUBJECT, textShareBuilder.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT, textShareBuilder.getText());
                intent.setComponent(new ComponentName("com.eqdd.yiqidian", "com.eqdd.yiqidian.ui.share.ShareEnterActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                baseActivity.startActivity(intent);
                break;
            case LINK:
              /*  LinkContent linkContent = new LinkContent(linkShareBuilder.getTitle(), linkShareBuilder.getText(), linkShareBuilder.getUrl(),
                        linkShareBuilder.getImageUrl(), linkShareBuilder.getSource(), linkShareBuilder.getId());
                AppConstent.messageContent = LinkMessage.obtain(linkContent);
                baseActivity.startActivity(new Intent(baseActivity, ShareEnterActivity.class));
                break;*/
        }
    }

    public void share2WorkCircle() {
        switch (type) {
            case IMG:

                break;
            case TEXT:

                break;
            case LINK:
                //todo 根据需求写
                Intent intent = new Intent(baseActivity, MainActivity.class);
                intent.putExtra("title", linkShareBuilder.getTitle());
                intent.putExtra("image", linkShareBuilder.getImageUrl());
                intent.putExtra("source", linkShareBuilder.getSource());
                intent.putExtra("url", linkShareBuilder.getUrl());
                intent.putExtra("id", linkShareBuilder.getId());
                baseActivity.startActivity(intent);
                break;
        }
    }

    public void share2Collection() {
        //todo 根据需求写,如果是添加到收藏,大概是将参数提交到网络请求
        switch (type) {
            case IMG:
                CollectionUtil.INSTANCE.chooseImg(imgShareBuilder.getImageUrl(), imgShareBuilder.getSource(), imgShareBuilder.getSourceOwner());
                break;
            case TEXT:
                CollectionUtil.INSTANCE.chooseText(textShareBuilder.getTitle(), textShareBuilder.getText(), textShareBuilder.getSource(), textShareBuilder.getSourceOwner());
                break;
            case LINK:
                CollectionUtil.INSTANCE.chooseLink(linkShareBuilder.getTitle(), linkShareBuilder.getUrl(), linkShareBuilder.getSource(),
                        linkShareBuilder.getSourceOwner(), linkShareBuilder.getId());
                break;

        }
    }

    public void share2WeXin(String platform) {
        switch (type) {
            case IMG:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_IMAGE);
                if (!TextUtils.isEmpty(imgShareBuilder.getImagePath())) {
                    shareParams.setImagePath(imgShareBuilder.getImagePath());
                    share(platform, shareParams);
                } else {
                    DownImage image = new DownImage(platform, shareParams);
                    image.execute(imgShareBuilder.getImageUrl());
                }
                break;
            case TEXT:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_TEXT);
                shareParams.setText(textShareBuilder.getText());//必须
                share(platform, shareParams);
                break;
            case LINK:
                shareParams = new ShareParams();
                shareParams.setTitle(linkShareBuilder.getTitle());
                shareParams.setText(linkShareBuilder.getText());
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setUrl(linkShareBuilder.getUrl());//必须
                DownImage image = new DownImage(platform, shareParams);
                image.execute(linkShareBuilder.getImageUrl());
                break;

        }


    }

    public void share2Q(String platform) {
        switch (type) {
            case IMG:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_IMAGE);
                if (TextUtils.isEmpty(imgShareBuilder.getImageUrl())) {
                    shareParams.setImagePath(imgShareBuilder.getImagePath());
                } else {
                    shareParams.setImageUrl(imgShareBuilder.getImageUrl());
                }
                break;
            case TEXT:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_TEXT);
                shareParams.setText(textShareBuilder.getText());
                shareParams.setTitle(textShareBuilder.getTitle());
                break;
            case LINK:
                shareParams = new ShareParams();
                shareParams.setTitle(linkShareBuilder.getTitle());
                shareParams.setText(linkShareBuilder.getText());
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setUrl(linkShareBuilder.getUrl());//必须
                if (TextUtils.isEmpty(linkShareBuilder.getImageUrl())) {
                    shareParams.setImagePath(linkShareBuilder.getImagePath());
                } else {
                    shareParams.setImageUrl(linkShareBuilder.getImageUrl());
                }
                break;
        }
        share(platform, shareParams);
    }

    public void share2Sina() {
        switch (type) {
            case TEXT:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_TEXT);
                shareParams.setText(textShareBuilder.getText());
                shareParams.setTitle(textShareBuilder.getTitle());
                share(SinaWeibo.Name, shareParams);
                break;
            case IMG:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_IMAGE);
                if (!TextUtils.isEmpty(imgShareBuilder.getImagePath())) {
                    shareParams.setImagePath(imgShareBuilder.getImagePath());
                    share(SinaWeibo.Name, shareParams);
                } else {
                    DownImage image = new DownImage(SinaWeibo.Name, shareParams);
                    image.execute(imgShareBuilder.getImageUrl());
                }

                break;
            case LINK:
                shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setText(linkShareBuilder.getText());
                shareParams.setUrl(linkShareBuilder.getUrl());
                DownImage image = new DownImage(SinaWeibo.Name, shareParams);
                image.execute(linkShareBuilder.getImageUrl());
        }
    }

    public void share2Explorer() {
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(linkShareBuilder.getUrl()));
        baseActivity.startActivity(in);
    }


    enum ShareType {
        TEXT,
        IMG,
        LINK,
        FILE,
        VIDEO
    }

    static void share(String platForm, ShareParams shareParams) {

        if (platForm.equals(QQ.Name)) {
            if (shareParams.getText() != null && shareParams.getTitle().length() > 30) {
                shareParams.setTitle(shareParams.getTitle().substring(0, 30));
            }

            if (shareParams.getText() != null && shareParams.getText().length() > 40) {
                shareParams.setText(shareParams.getText().substring(0, 40));
            }
        }


        JShareInterface.share(platForm, shareParams, new PlatActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                System.out.println("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable throwable) {
                System.out.println("分享失败" + throwable.toString());
                if (platform.getName().equals(QQ.Name) || platform.getName().equals(QZone.Name)) {
                    Toast.makeText(MyApplication.instance, "请安装QQ", Toast.LENGTH_SHORT).show();
                } else if (platform.getName().equals(Wechat.Name) || platform.getName().equals(WechatFavorite.Name)
                        || platform.getName().equals(WechatMoments.Name)) {
                    Toast.makeText(MyApplication.instance, "请安装Wechat", Toast.LENGTH_SHORT).show();

                } else if (platform.getName().equals(SinaWeibo.Name)) {
                    Toast.makeText(MyApplication.instance, "请安装微博", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                System.out.println("分享取消");
            }
        });
    }


    /**
     * 分享时,将图片链接转为bitmap
     */
    static class DownImage extends AsyncTask<String, Bitmap, Bitmap> {

        private String platform;
        private ShareParams shareParams;

        DownImage(String platform, ShareParams shareParams) {
            this.platform = platform;
            this.shareParams = shareParams;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                String strUrl = params[0];
                URL url = new URL(strUrl);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
                bitmap = BitmapFactory.decodeStream(inStream);
            } catch (Exception e) {
                bitmap = BitmapFactory.decodeResource(MyApplication.instance.getResources(), R.mipmap.ic_launcher);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //bitmap = compressBitmap(bitmap);
            shareParams.setImageData(bitmap);
            share(platform, shareParams);
        }

        /**
         * 当需要限制bitmap大小时
         * 将图片压缩到指定大小之下
         *
         * @param bitmap 原bitmap
         * @return 目标bitmap
         * @throws IOException
         */
        private Bitmap compressBitmap(Bitmap bitmap) throws IOException {
            int topLimit = 30 * 1024;
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                Log.w("xxx", baos.toByteArray().length + "");
                while (baos.toByteArray().length > topLimit) {
                    baos.reset();
                    options -= 5;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
                bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成
                isBm.close();
            }
            return bitmap;
        }

    }


}
