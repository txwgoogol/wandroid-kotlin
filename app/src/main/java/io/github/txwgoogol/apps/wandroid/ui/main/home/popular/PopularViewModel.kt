package io.github.txwgoogol.apps.wandroid.ui.main.home.popular

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article

//热门
class PopularViewModel : BaseViewModel() {

    //初始化接口对象
    private val popularRepository by lazy { PopularRepository() }

    //文章列表
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    //加载更多状态
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()

    //刷新状态
    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()

    //重新加载状态
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    private var page = INITIAL_PAGE

    //初始化
    fun refreshArticleList() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                //置顶文章
                val articleTopDeferred = async {
                    popularRepository.articleTop()
                }

                //最新文章
                val paginationDeferred = async {
                    popularRepository.articleList(INITIAL_PAGE)
                }

                //添加置顶标签
                val articleTop = articleTopDeferred.await().onEach { it.top = true }
                val pagination = paginationDeferred.await()
                page = pagination.curPage

                //将两个数据合并
                articleList.value = (articleTop + pagination.datas).toMutableList()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    //加载更多
    fun loadMoreArticleList() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = popularRepository.articleList(page)
                page = pagination.curPage
                articleList.value = articleList.value.concat(pagination.datas)

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            }, error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            })
    }

}