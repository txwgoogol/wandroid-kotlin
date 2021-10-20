package io.github.txwgoogol.apps.jetpack.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.github.txwgoogol.apps.jetpack.base.BaseActivity
import io.github.txwgoogol.apps.jetpack.databinding.TestActBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

//协程 ViewBinding Kotlin操作符使用 内联 外联 使用
class TestActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelLayer(10086) }
    private val binding by lazy { TestActBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.result.observe(this, { binding.test.text = it.toString() })
    }

}


//实体类
data class DataModel(val code: Int, val message: String = "") {
    override fun toString(): String {
        return "DataModel(code=$code, message='$message')"
    }
}

//接口请求
object RepositoryLayer {
    suspend fun getSomeData(id: Int): DataModel = withContext(Dispatchers.IO) {
        delay(2000)
        DataModel(200, "Result$id")
    }
}

//接口请求
class ViewModelLayer(private val id: Int) : ViewModel() {
    val result = liveData {
        try {
            emit(DataModel(0, "!!Loading"))
            emit(RepositoryLayer.getSomeData(id))
        } catch (e: Exception) {
            emit(DataModel(-1, "error"))
        }
    }
}