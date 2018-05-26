package com.jhj.jshare.jshare

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jiguang.share.qqmodel.QQ
import cn.jiguang.share.qqmodel.QZone
import cn.jiguang.share.wechat.Wechat
import cn.jiguang.share.wechat.WechatFavorite
import cn.jiguang.share.wechat.WechatMoments

import com.jhj.jshare.R
import com.jhj.jshare.jshare.bean.GridBean
import com.jhj.jshare.jshare.bean.ImgShareBuilder
import com.jhj.jshare.jshare.bean.LinkShareBuilder
import com.jhj.jshare.jshare.bean.TextShareBuilder
import com.jhj.prompt.dialog.alert.AlertFragment
import com.jhj.prompt.dialog.alert.constants.DialogStyle
import com.jhj.prompt.dialog.alert.interfaces.OnCustomListener
import kotlinx.android.synthetic.main.layout_grid_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

import java.util.ArrayList

/**
 * @author吕志豪 .
 * @date 18-4-14  上午10:34.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

class JShareDialog internal constructor(private val activity: Activity) {


    private lateinit var textShareBuilder: TextShareBuilder
    private lateinit var imgShareBuilder: ImgShareBuilder
    private lateinit var linkShareBuilder: LinkShareBuilder
    private var isTextShare = false
    private var isImgShare = false
    private var isLinkShare = false

    constructor(activity: Activity, builder: ImgShareBuilder) : this(activity) {
        this.imgShareBuilder = builder
        isImgShare = true
        showDialog()
    }

    constructor(activity: Activity, builder: TextShareBuilder) : this(activity) {
        this.textShareBuilder = builder
        isTextShare = true
        showDialog()
    }

    constructor(activity: Activity, builder: LinkShareBuilder) : this(activity) {
        this.linkShareBuilder = builder
        isLinkShare = true
        showDialog()
    }


    private fun showDialog() {


        AlertFragment.Builder(activity)
                .setDialogStyle(DialogStyle.DIALOG_BOTTOM)
                .setBackgroundResource(R.drawable.bg_dialog_no_corner)
                .setPaddingHorizontal(0)
                .setPaddingBottom(0)
                .setLayoutRes(R.layout.layout_share_dialog, object : OnCustomListener {
                    override fun onLayout(view: View) {
                        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                        recyclerView.layoutManager = GridLayoutManager(activity, 4)
                        val gridBeans = ArrayList<GridBean>()
                        gridBeans.add(GridBean("发送给好友", R.mipmap.ic_share_friend, 0))
                        gridBeans.add(GridBean("工作圈", R.mipmap.ic_share_eqd, 1))
                        gridBeans.add(GridBean("我的收藏", R.mipmap.ic_share_shoucang, 2))
                        gridBeans.add(GridBean("微信", R.mipmap.ic_share_wechat, 3))
                        gridBeans.add(GridBean("微信收藏", R.mipmap.ic_share_wechat_fav, 4))
                        gridBeans.add(GridBean("朋友圈", R.mipmap.ic_share_wechat_moment, 5))
                        gridBeans.add(GridBean("QQ", R.mipmap.ic_share_qq, 6))
                        gridBeans.add(GridBean("qq空间", R.mipmap.ic_share_qzone, 7))
                        gridBeans.add(GridBean("新浪微博", R.mipmap.ic_share_weibo, 8))
                        if (isLinkShare) {
                            gridBeans.add(GridBean("用浏览器打开", R.mipmap.ic_share_liulanqi, 9))
                        }
                        val adapter = GridAdapter()
                        adapter.dataList = gridBeans
                        recyclerView.adapter = adapter
                    }
                })
                .show()

    }


    inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {


        var dataList: List<GridBean> = arrayListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(activity).inflate(R.layout.layout_grid_item, parent, false)
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val bean = dataList[position]
            with(holder.itemView) {
                tag = bean
                tv_info.text = bean.title
                iv_icon.setImageResource(bean.getResId())
            }

        }


        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            init {
                itemView.onClick {
                    val bean = itemView.tag as GridBean
                    when (bean.getPos()) {
                        0 -> shareTo()?.share2EQD()
                        1 -> shareTo()?.share2WorkCircle()
                        2 -> shareTo()?.share2Collection()
                        3 -> shareTo()?.share2WeXin(Wechat.Name) //微信
                        4 -> shareTo()?.share2WeXin(WechatFavorite.Name) //微信收藏
                        5 -> shareTo()?.share2WeXin(WechatMoments.Name) //朋友圈
                        6 -> shareTo()?.share2Q(QQ.Name) //QQ
                        7 -> shareTo()?.share2Q(QZone.Name) //QQ空间
                        8 -> shareTo()?.share2Sina() //新浪
                        9 -> shareTo()?.share2Explorer() //浏览器
                    }

                }
            }

            private fun shareTo(): JShareUtil? {
                if (isImgShare)
                    return JShareUtil(imgShareBuilder, activity)
                if (isTextShare)
                    return JShareUtil(textShareBuilder, activity)
                if (isLinkShare)
                    return JShareUtil(linkShareBuilder, activity)
                return null
            }

        }
    }

}
