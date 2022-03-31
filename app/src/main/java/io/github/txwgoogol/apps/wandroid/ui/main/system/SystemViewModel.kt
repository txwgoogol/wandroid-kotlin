package io.github.txwgoogol.apps.wandroid.ui.main.system

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.model.bean.Category

//体系
class SystemViewModel : BaseViewModel() {

    private val repository by lazy { SystemRepository() }

    val treeList: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun tree() {
        launch(
            block = {
                treeList.value = repository.tree()
            },
            error = {
                reloadStatus.value = true
            }
        )
    }

}