package io.github.txwgoogol.apps.wandroid.ui.main.home.plaza

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article

//广场
class PlazaViewModel : BaseViewModel() {

    private val plazaRepository by lazy { PlazaRepository() }

    val articlePlazaList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    private var page = INITIAL_PAGE

    fun refreshArticlePlaza() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                val plazaArticleDefer = plazaRepository.plazaArticleList(INITIAL_PAGE)
                page = plazaArticleDefer.curPage
                articlePlazaList.value = plazaArticleDefer.datas.toMutableList()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            })
    }

    fun loadMoreArticlePlaza() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val pagination = plazaRepository.plazaArticleList(page)
                articlePlazaList.value = articlePlazaList.value.concat(pagination.datas)

                page = pagination.curPage

                loadMoreStatus.value =
                    if (pagination.offset >= pagination.total) LoadMoreStatus.END
                    else LoadMoreStatus.ERROR

            },
            error = {
                reloadStatus.value = true
            })
    }

}