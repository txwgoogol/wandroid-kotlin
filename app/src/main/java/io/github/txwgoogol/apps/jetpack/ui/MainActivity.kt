package io.github.txwgoogol.apps.jetpack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.github.txwgoogol.apps.jetpack.databinding.MainActBinding
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private val binding by lazy { MainActBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //ks
        val data = Project(name = "kotlinx.serialization", language = "Kotlin")
        val string = Json.encodeToString(data)
        Logger.d("string=$string")
        val obj = Json.decodeFromString<Project>(string)
        Logger.d("obj=$obj")

        //
        //val moshiData = MoShiProject(name = "moshi", language = "Kotlin")
        //val moshi:Moshi = Moshi.Builder().build()
        //val jsonAdapter : JsonAdapter<MoShiProject> = moshi.adapter<MoShiProject>()
        //Logger.d("moshi-string=${jsonAdapter.toJson(moshiData)}")
        //val moshiObj =
        //Logger.d("moshi-obj=${}")




        //val blackjackHand = MoShiProject(name = "moshi", language = "Kotlin")

        //val moshi: Moshi = Moshi.Builder().build()
        //val jsonAdapter: JsonAdapter<MoShiProject> = moshi.adapter<MoShiProject>()

        //val json: String = jsonAdapter.toJson(blackjackHand)
        //Logger.d("json=${jsonAdapter.fromJson(json)}")


    }

}

@Serializable
data class Project(val name: String = "", val language: String = "")


data class MoShiProject(val name: String = "", val language: String = "")