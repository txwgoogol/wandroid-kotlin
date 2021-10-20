package io.github.txwgoogol.apps.jetpack.vm

import androidx.lifecycle.liveData
import io.github.txwgoogol.apps.jetpack.api.ApiStores
import io.github.txwgoogol.apps.jetpack.base.BaseViewModel
import io.github.txwgoogol.apps.jetpack.db.Base

class DataViewModel(val id: String) : BaseViewModel() {

    val result = liveData<Result<Base>> {
        val response = ApiStores.create().get(id)
        if (response.isSuccess) {
            //emit(response.data.toString())
        }
    }

}