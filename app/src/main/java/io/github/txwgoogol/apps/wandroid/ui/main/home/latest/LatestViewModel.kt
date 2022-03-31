package io.github.txwgoogol.apps.wandroid.ui.main.home.latest

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article

//最新
class LatestViewModel : BaseViewModel() {

    private val latestResponse by lazy { LatestRepository() }

    val articleLatestList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    private var page = INITIAL_PAGE

    fun refreshLatestArticleList() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val paginationDeferred = async {
                    latestResponse.articleLatest(INITIAL_PAGE)
                }
                val pagination = paginationDeferred.await()
                page = pagination.curPage
                articleLatestList.value = pagination.datas.toMutableList()

                refreshStatus.value = false
            }, error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticleList() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = latestResponse.articleLatest(page)
                page = pagination.curPage
                articleLatestList.value = articleLatestList.value.concat(pagination.datas)

                loadMoreStatus.value = if (pagination.total <= pagination.offset) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                LoadMoreStatus.ERROR
            }
        )
    }

}