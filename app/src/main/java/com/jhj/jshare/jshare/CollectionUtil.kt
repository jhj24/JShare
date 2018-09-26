package com.jhj.jshare.jshare


/**
 *
 * Created by jhj on 18-5-25.
 */
object CollectionUtil {

    private const val OWNER = "owner"
    private const val TYPE = "type"
    private const val SOURCE = "source"
    private const val SOURCE_OWNER = "sourceOwner"

    private const val URL = "url"
    private const val TITLE = "title"
    private const val CONTENT = "ccontent"
    private const val ARTICLE_ID = "articleId"

    fun chooseText(title: String, ccontent: String, source: String, sourceOwner: String) {
        val params = hashMapOf<String, String>()
        params.put(TITLE, title)
        params.put(CONTENT, ccontent)
        baseChoose(2, source, sourceOwner, params)
    }

    fun chooseImg(url: String, source: String, sourceOwner: String) {
        val params = hashMapOf<String, String>()
        params.put(URL, url)
        baseChoose(8, source, sourceOwner, params)
    }

    fun chooseImgAndText(title: String, ccontent: String, url: String, source: String, sourceOwner: String) {
        val params = hashMapOf<String, String>()
        params.put(URL, url)
        params.put(TITLE, title)
        params.put(CONTENT, ccontent)
        baseChoose(9, source, sourceOwner, params)
    }


    fun chooseLink(title: String, url: String, source: String, sourceOwner: String, articleId: String) {
        //type说明:  10 易企阅  12 易企创  11 易企学    0:工作圈   1:网页连接，2：视频，3：音乐，9：广告
        var type: Int? = null
        if (source == "易企阅") {
            type = 10
        } else if (source == "易企学") {
            type = 11
        } else if (source == "易企创") {
            type = 12
        } else if (source == "工作圈") {
            type = 0
        }
        if (type != null) {
            val params = hashMapOf<String, String>()
            params.put(URL, url)
            params.put(TITLE, title)
            params.put(ARTICLE_ID, articleId)
            baseChoose(type, source, sourceOwner, params)
        } else {
            //ToastUtil.showShort("暂不支持收藏")
            return
        }
    }


    private fun baseChoose(type: Int, source: String, sourceOwner: String, params: HashMap<String,String>) {
        /*OkGo.post<HttpResult<*>>(HttpConfig.BASE_URL + HttpConfig.ADD_COLLECTION)
                .params(params)
                .params(TYPE, type)
                .params(OWNER, AppConstent.user.guid)
                .params(SOURCE, source)
                .params(SOURCE_OWNER, sourceOwner)
                .execute(object : JsonCallBack<HttpResult<*>>() {
                    override fun onSuccess(response: Response<HttpResult<*>>) {
                        val httpResult = response.body()
                        if (httpResult.status == 200) {
                            ToastUtil.showShort("收藏成功")
                        }
                    }

                    override fun onError(response: Response<HttpResult<*>>?) {
                        super.onError(response)
                        ToastUtil.showShort("收藏失败")
                    }

                })*/
    }


}