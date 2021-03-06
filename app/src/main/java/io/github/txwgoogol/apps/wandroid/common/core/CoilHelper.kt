package io.github.txwgoogol.apps.wandroid.common.core

import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import okhttp3.OkHttpClient

//图片加载帮助类
object CoilHelper {

    @JvmStatic
    fun init(context: Context) {
        val imageView = ImageLoader.Builder(context)
            .crossfade(200)
            .okHttpClient(
                OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(context)).build()
            )
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }
                add(SvgDecoder(context))
                add(VideoFrameFileFetcher(context))
                add(VideoFrameUriFetcher(context))
            }.build()
        Coil.setImageLoader(imageView)
    }

    fun ImageView.load(
        lifecycle: Lifecycle? = null,
        url: String? = null,
        placeholder: Int = 0,
        fallback: Int = 0,
        error: Int = 0,
        circleCrop: Boolean = false,
        cornerRadius: Float = 0f
    ) {
        this.load(url, context.imageLoader) {
            if (lifecycle != null) {
                lifecycle(lifecycle)
            }
            if (placeholder != 0) {
                placeholder(placeholder)
            }
            if (fallback != 0) {
                fallback(fallback)
            }
            if (error != 0) {
                error(error)
            }
            if (circleCrop) {
                transformations(CircleCropTransformation())
            }
            if (cornerRadius > 0) {
                transformations(coil.transform.RoundedCornersTransformation(cornerRadius))
            }
        }
    }

}