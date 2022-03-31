package io.github.txwgoogol.apps.wandroid.ui.main.home.wechat

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article
import io.github.txwgoogol.apps.wandroid.model.bean.Category

//公众号
class WechatViewModel : BaseViewModel() {

    private val repository by lazy { WechatRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val wechatList: MutableLiveData<MutableList<Category>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadListStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()

    private var page = INITIAL_PAGE

    fun firstArticle() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val category = repository.category()
                wechatList.value = category

                val articles = repository.wxArticleList(category[INITIAL_CHECKED].id, INITIAL_PAGE)
                articleList.value = articles.datas

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun refreshArticle(checkPosition: Int = INITIAL_CHECKED) {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val category = wechatList.value ?: return@launch
                val articles = repository.wxArticleList(category[0].id, INITIAL_PAGE)
                articleList.value = articles.datas

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadListStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticle() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val category = wechatList.value ?: return@launch
                val pagination = repository.wxArticleList(category[0].id, page)
                articleList.value = articleList.value.concat(pagination.datas)
                page = pagination.curPage

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            })
    }

}