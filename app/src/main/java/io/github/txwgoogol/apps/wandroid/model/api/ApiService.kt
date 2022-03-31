package io.github.txwgoogol.apps.wandroid.model.api

import io.github.txwgoogol.apps.wandroid.model.bean.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

//接口
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    // ================== 首页 热门 最新 广场 项目 公众号 start ==================
    /**
     * 热门
     * https://www.wanandroid.com/article/top/json
     * 方法：GET
     * 参数：无
     */
    @GET("article/top/json")
    suspend fun articleTop(): ApiResult<MutableList<Article>>

    /**
     * 热门 - 最新
     * https://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun articleList(@Path("page") id: Int = 0): ApiResult<Pagination<Article>>

    /**
     * 项目
     * https://wanandroid.com/article/listproject/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/listproject/{page}/json")
    suspend fun projectList(@Path("page") id: Int = 0): ApiResult<Pagination<Article>>

    /**
     * 广场
     * https://wanandroid.com/user_article/list/页码/json
     * 方法：GET
     * 参数：页码：拼接在链接上，从0开始。
     */
    @GET("user_article/list/{index}/json")
    suspend fun articlePlaza(@Path("index") index: Int = 0): ApiResult<Pagination<Article>>

    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     * 方法：GET
     * 参数：无
     */
    @GET("project/tree/json")
    suspend fun projectCategories(): ApiResult<MutableList<Category>>

    /**
     * 项目列表数据
     * 某一个分类下项目列表数据，分页展示
     * https://www.wanandroid.com/project/list/1/json?cid=294
     * 方法：GET
     * 参数：cid: 分类的id，上面项目分类接口
     *      页码: 拼接在链接中，从1开始。
     */
    @GET("project/list/{page}/json")
    suspend fun projectListByCid(
        @Path("page") index: Int = 0,
        @Query("cid") cid: Int = 0
    ): ApiResult<Pagination<Article>>

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     * 方法：GET
     * 参数：无
     */
    @GET("wxarticle/chapters/json")
    suspend fun wxArticle(): ApiResult<MutableList<Category>>

    /**
     * 查看某个公众号历史数据
     * https://wanandroid.com/wxarticle/list/408/1/json
     * 方法：GET
     * 参数：公众号 ID：拼接在 url 中，eg:405
     *      公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun wxArticleList(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResult<Pagination<Article>>
    // ================== 首页 热门 最新 广场 项目 公众号 end ==================


    // ================== 体系 start ==================
    /**
     * 2.1 体系数据
     * https://www.wanandroid.com/tree/json
     * 方法：GET
     * 参数：无
     */
    @GET("tree/json")
    suspend fun tree(): ApiResult<MutableList<Category>>

    /**
     * 2.2 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     * 方法：GET
     * 参数：cid 分类的id，上述二级目录的id
     *      页码 拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun treeArticle(
        @Path("page") index: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>

    /**
     * 2.3 按照作者昵称搜索文章
     * https://wanandroid.com/article/list/0/json?author=鸿洋
     * 方法：GET
     * 参数：拼接在链接上，从0开始。author：作者昵称，不支持模糊匹配。
     */
    @GET("article/list/{page}/json")
    suspend fun treeArticleAuthor(
        @Path("page") index: Int,
        @Query("author") author: String
    ): ApiResult<Pagination<Article>>
    // ================== 体系 end ==================


    // ================== 发现 start ==================
    /**
     * 1.2 首页banner
     * https://www.wanandroid.com/banner/json
     * 方法：GET
     * 参数：无
     */
    @GET("banner/json")
    suspend fun banner(): ApiResult<MutableList<Banner>>

    /**
     * 1.3 常用网站
     * https://www.wanandroid.com/friend/json
     * 方法：GET
     * 参数：无
     */
    @GET("friend/json")
    suspend fun friend(): ApiResult<MutableList<Frequently>>

    /**
     * 1.4 搜索热词
     * https://www.wanandroid.com/hotkey/json
     * 方法：GET
     * 参数：无
     */
    @GET("hotkey/json")
    suspend fun hotkey(): ApiResult<MutableList<HotKey>>
    // ================== 发现 start ==================


    /**
     * 3.1 导航
     * https://www.wanandroid.com/navi/json
     * 方法：GET
     * 参数：无
     */
    @GET("navi/json")
    suspend fun nav(): ApiResult<MutableList<Nav>>


    /**
     * 5.1 登录
     * https://www.wanandroid.com/user/login
     * 方法：POST
     * 参数：username，password
     * @return 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @POST("user/login")
    suspend fun login(): ApiResult<String>

    /**
     * 5.2 注册
     * https://www.wanandroid.com/user/register
     * 方法：POST
     * 参数：username，password
     * @return 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @POST("user/register")
    suspend fun register(): ApiResult<String>

    /**
     * 5.3 退出
     * https://www.wanandroid.com/user/logout/json
     * 方法：GET
     * 参数：无
     * @return 访问了 logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），如果客户端 Cookie 实现合理，可以实现自动清理，如果本地做了用户账号密码和保存，及时清理。
     */
    @GET("user/logout/json")
    suspend fun logout(): ApiResult<String>


    /**
     * 6.1 收藏文章列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     * 方法：GET
     * 参数：页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{index}/json")
    suspend fun collect(@Path("index") index: Int): ApiResult<String>


    /**
     * 6.2 收藏站内文章
     * https://www.wanandroid.com/lg/collect/1165/json
     * 方法：POST
     * 参数：文章id，拼接在链接中。
     */
    @POST("lg/collect/{index}/json")
    suspend fun collectLocal(@Path("index") index: Int): ApiResult<String>

    /**
     * 6.3 收藏站外文章
     * https://www.wanandroid.com/lg/collect/add/json
     * 方法：POST
     * 参数：文章id，拼接在链接中。
     */
    @POST("lg/collect/add/json")
    suspend fun collectThreeAdd(@Path("index") index: Int): ApiResult<String>


    /**
     * 6.3.1 编辑收藏的文章，支持站内，站外
     * https://wanandroid.com/lg/collect/user_article/update/文章id/json
     * 方法：POST
     * 参数：文章 id:拼接在 url 上  title: 文章标题  link: 文章 url  author: 作者
     * 注意：调用此接口，一定要带上 title,link,author，否则会认为想设置为""。
     */
    @POST("lg/collect/user_article/update/{id}/json")
    suspend fun collectUpdate(@Path("id") index: Int): ApiResult<String>

    /**
     * 取消收藏一共有两个地方可以触发
     * 6.4.1 文章列表
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     * 方法：POST
     * 参数：id:拼接在链接上
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectOrigin(@Path("id") index: Int): ApiResult<String>

    /**
     * 取消收藏一共有两个地方可以触发
     * 6.4.2 我的收藏页面（该页面包含自己录入的内容）
     * https://www.wanandroid.com/lg/uncollect/2805/json
     * 方法：POST
     * 参数：id:拼接在链接上 originId:列表页下发，无则为-1
     */
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollect(@Path("id") index: Int): ApiResult<String>

    /**
     * 6.5 收藏网站列表
     * https://www.wanandroid.com/lg/collect/usertools/json
     * 方法：GET
     * 参数：无
     */
    @POST("lg/collect/usertools/json")
    suspend fun collectUserTools(): ApiResult<String>

    /**
     * 6.6 收藏网址
     * https://www.wanandroid.com/lg/collect/addtool/json
     * 方法：POST
     * 参数：name,link
     */
    @POST("lg/collect/addtool/json")
    suspend fun collectAddTool(
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResult<String>

    /**
     * 6.7 编辑收藏网站
     * https://www.wanandroid.com/lg/collect/updatetool/json
     * 方法：POST
     * 参数：id,name,link
     */
    @POST("lg/collect/updatetool/json")
    suspend fun collectAddTool(
        @Query("id") id: String,
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResult<String>


    /**
     * 6.8 删除收藏网站
     * https://www.wanandroid.com/lg/collect/deletetool/json
     * 方法：POST
     * 参数：id
     */
    @POST("lg/collect/deletetool/json")
    suspend fun collectDeleteTool(@Query("id") id: String): ApiResult<String>


    /**
     * 7.1 搜索
     * https://www.wanandroid.com/article/query/0/json
     * 方法：POST
     * 参数：页码：拼接在链接上，从0开始。k ： 搜索关键词
     * 注意：支持多个关键词，用空格隔开
     */
    @POST("article/query/{index}/json")
    suspend fun articleQuery(@Query("index") id: String, @Query("k") k: String): ApiResult<String>


    /**
     * 8.1 TODO工具
     * https://www.wanandroid.com/lg/todo/add/json
     * 方法：POST
     * 参数：title: 新增标题（必须）
     *      content: 新增详情（必须）
     *      date: 2018-08-01 预定完成时间（不传默认当天，建议传）
     *      type: 大于0的整数（可选）；
     *      工作1；生活2；娱乐3； 如果不设置type则为 0，未来无法做 type=0的筛选，会显示全部（筛选 type 必须为大于 0 的整数）
     *      priority 大于0的整数（可选）；重要（1）一般（2）等
     */
    @GET("lg/todo/add/json")
    suspend fun todoAdd(@Query("index") id: String): ApiResult<String>

    /**
     * 8.2 更新一个Todo
     * https://www.wanandroid.com/lg/todo/update/83/json
     * 方法：POST
     * 参数：
     *     id: 拼接在链接上，为唯一标识，列表数据返回时，每个todo 都会有个id标识 （必须）
     *     title: 新增标题（必须）
     *     content: 新增详情（必须）
     *     date: 2018-08-01 预定完成时间（不传默认当天，建议传）
     *     status:0 0为未完成，1为完成
     *     type: 大于0的整数（可选）；
     *     工作1；生活2；娱乐3； 如果不设置type则为 0，未来无法做 type=0的筛选，会显示全部（筛选 type 必须为大于 0 的整数）
     *     priority 大于0的整数（可选）；重要（1）一般（2）等
     */
    @GET("lg/todo/update/{id}/json")
    suspend fun todoUpdate(@Query("id") id: String): ApiResult<String>


    /**
     * 8.2 删除一个Todo
     * https://www.wanandroid.com/lg/todo/delete/83/json
     * 方法：POST
     * 参数：id: 拼接在链接上，为唯一标识
     */
    @POST("lg/todo/delete/{id}/json")
    suspend fun todoDelete(@Query("id") id: String): ApiResult<String>

    /**
     * 8.3 仅更新完成状态Todo
     * https://www.wanandroid.com/lg/todo/done/80/json
     * 方法：POST
     * 参数：id: 拼接在链接上，为唯一标识
     *  status: 0或1，传1代表未完成到已完成，反之则反之。
     * 只会变更status，未完成->已经完成 or 已经完成->未完成。
     */
    @POST("lg/todo/done/80/json")
    suspend fun todoDone(@Query("id") id: String): ApiResult<String>

    /**
     * 8.4 TODO 列表
     * https://www.wanandroid.com/lg/todo/v2/list/页码/json
     * 方法：POST
     * 参数：页码从1开始，拼接在url 上
     * status 状态， 1-完成；0未完成; 默认全部展示；
     * type 创建时传入的类型, 默认全部展示
     * priority 创建时传入的优先级；默认全部展示
     * orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     *
     * 注意!!!：page 从1开始
     */
    @GET("lg/todo/v2/list/{index}/json")
    suspend fun todoList(@Query("index") id: Int = 1): ApiResult<String>


    /**
     * 9.0 个人信息
     * //https://wanandroid.com/user/lg/userinfo/json
     * 方法：GET
     * 参数：页码：拼接在链接上，从0开始。
     */
    @GET("user/lg/userinfo/json")
    suspend fun userinfo(): ApiResult<Profile>

    /**
     * 9.1 积分排行榜接口
     * https://www.wanandroid.com/coin/rank/1/json
     * 方法：GET
     * 参数：页码：拼接在链接上，从0开始。
     */
    @GET("coin/rank/{index}/json")
    suspend fun coinRank(@Query("index") id: String): ApiResult<String>

    /**
     * 9.2 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     * 方法：POST
     * 参数：无
     */
    @GET("lg/coin/userinfo/json")
    suspend fun coin(): ApiResult<String>

    /**
     * 9.3 获取个人积分获取列表，需要登录后访问
     * https://www.wanandroid.com/lg/coin/list/1/json
     * 方法：GET
     * 参数：页码：拼接在链接上，从0开始。
     */
    @GET("lg/coin/list/1/json")
    suspend fun coinList(): ApiResult<String>


    /**
     * 10.2 分享人对应列表数据
     * https://www.wanandroid.com/user/2/share_articles/页码/json
     * 方法：GET
     * 参数：用户id: 拼接在url上 页码拼接在url上从1开始
     */
    @GET("user/{id}/share_articles/{index}/json")
    suspend fun articleShare(@Path("id") id: String, @Path("index") index: Int): ApiResult<String>

    /**
     * 10.3 自己的分享的文章列表
     * https://wanandroid.com/user/lg/private_articles/1/json
     * 方法：GET
     * 参数：页码，从1开始
     */
    @GET("user/lg/private_articles/{index}/json")
    suspend fun articleSharePrivate(@Path("index") index: Int): ApiResult<String>

    /**
     * 10.4 删除自己分享的文章
     * https://wanandroid.com/lg/user_article/delete/9475/json
     * 方法：POST
     * 参数：文章id，拼接在链接上
     */
    @GET("lg/user_article/delete/{id}/json")
    suspend fun articleSharePrivateDelete(@Path("id") index: Int): ApiResult<String>

    /**
     * 10.5 分享文章
     * https://www.wanandroid.com/lg/user_article/add/json
     * 方法：POST
     * 参数：title:   link:
     */
    @GET("lg/user_article/add/json")
    suspend fun articleShare(
        @Query("title") title: String,
        @Query("link") link: String
    ): ApiResult<String>


    /**
     * 11 问答
     * https://wanandroid.com/wenda/list/1/json
     * 方法：GET
     * 参数：pageId,拼接在链接上，例如上面的1
     */
    @GET("wenda/list/{index}/json")
    suspend fun qa(@Query("index") title: String): ApiResult<String>


}