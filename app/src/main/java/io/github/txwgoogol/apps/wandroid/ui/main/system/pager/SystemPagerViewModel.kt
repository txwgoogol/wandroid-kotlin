package io.github.txwgoogol.apps.wandroid.ui.main.system.pager

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article
import io.github.txwgoogol.apps.wandroid.model.bean.Pagination

//体系
class SystemPagerViewModel : BaseViewModel() {

    private val repository by lazy { SystemPagerRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()

    private var page = INITIAL_PAGE

    fun refreshArticle(cid: Int) {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val pagination = repository.treeArticle(INITIAL_PAGE, cid)
                page = pagination.curPage

                articleList.value = pagination.datas

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun loadMoreArticle(cid: Int) {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = repository.treeArticle(page, cid)
                page = pagination.curPage

                articleList.value = articleList.value.concat(pagination.datas)

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }

            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}