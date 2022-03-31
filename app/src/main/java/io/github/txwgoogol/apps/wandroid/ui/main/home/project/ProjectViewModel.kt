package io.github.txwgoogol.apps.wandroid.ui.main.home.project

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.common.loadmore.LoadMoreStatus
import io.github.txwgoogol.apps.wandroid.ext.concat
import io.github.txwgoogol.apps.wandroid.model.bean.Article
import io.github.txwgoogol.apps.wandroid.model.bean.Category

//项目
class ProjectViewModel : BaseViewModel() {

    private val projectRepository by lazy { ProjectRepository() }

    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val checkCategory: MutableLiveData<Int> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadListStatus: MutableLiveData<Boolean> = MutableLiveData()

    var page = INITIAL_PAGE

    fun projectCategories() {
        launch(block = {
            refreshStatus.value = true
            reloadStatus.value = false

            val categoryList = projectRepository.projectCategories()
            categories.value = categoryList //项目分类

            //记录分类位置
            val checkedPosition = INITIAL_CHECKED
            val cid = categoryList[checkedPosition].id
            //checkCategory.value = 0

            //查询项目内部文章
            val pagination = projectRepository.projectListByCid(INITIAL_PAGE_ONE, cid)
            page = pagination.curPage
            articleList.value = pagination.datas

            refreshStatus.value = false
        }, error = {
            refreshStatus.value = false
            reloadStatus.value = true
        })
    }

    //下拉刷新
    fun refreshArticleList(checkPosition: Int = checkCategory.value ?: INITIAL_CHECKED) {
        launch(
            block = {
                refreshStatus.value = true
                reloadListStatus.value = false

//                if (checkPosition != checkCategory.value) {
//                    articleList.value = mutableListOf()
//                    checkCategory.value = checkPosition
//                }

                val categoryLis = categories.value ?: return@launch
                val cid = categoryLis[INITIAL_CHECKED].id
                val pagination = projectRepository.projectListByCid(INITIAL_PAGE, cid)
                page = pagination.curPage
                articleList.value = pagination.datas

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadListStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    //加载更多
    fun loadMoreArticleList() {
        launch(
            block = {
                loadMoreStatus.value = LoadMoreStatus.LOADING

                val categoryList = categories.value ?: return@launch
                //val checkPosition = checkCategory.value ?: return@launch
                val cid = categoryList[INITIAL_CHECKED].id
                val pagination = projectRepository.projectListByCid(page,cid)
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
            }
        )
    }

}