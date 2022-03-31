package io.github.txwgoogol.apps.wandroid.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.htmlEncode
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.module.LoadMoreModule
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVBQuickAdapter
import io.github.txwgoogol.apps.wandroid.base.BaseVBViewHolder
import io.github.txwgoogol.apps.wandroid.common.loadmore.BaseVBLoadMoreAdapter
import io.github.txwgoogol.apps.wandroid.databinding.ArticleItemBinding
import io.github.txwgoogol.apps.wandroid.ext.htmlToSpanned
import io.github.txwgoogol.apps.wandroid.model.bean.Article

class ArticleAdapter : BaseVBLoadMoreAdapter<Article, ArticleItemBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = ArticleItemBinding.inflate(inflater, parent, false)

    override fun convert(holder: BaseVBViewHolder<ArticleItemBinding>, item: Article) {
        holder.run {
            vb.tvAuthor.text = when {
                !item.author.isNullOrEmpty() -> {
                    item.author
                }
                !item.shareUser.isNullOrEmpty() -> {
                    item.shareUser
                }
                else -> {
                    context.getString(R.string.app_name)
                }
            }
            vb.tvTop.isVisible = item.top
            vb.tvFresh.isVisible = item.fresh && !item.top
            vb.tvTag.visibility = if (item.tags.isNotEmpty()) {
                vb.tvTag.text = item.tags[0].name
                View.VISIBLE
            } else {
                View.GONE
            }
            vb.tvChapter.text = when {
                !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() -> {
                    "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                }
                item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    item.chapterName.htmlToSpanned()
                !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                    item.superChapterName.htmlToSpanned()
                else -> ""
            }
            vb.tvTitle.text = item.title.htmlToSpanned()
            vb.tvDesc.text = item.desc.htmlToSpanned()
            vb.tvDesc.isGone = item.desc.isNullOrEmpty() //如果没有内容 则隐藏
            vb.tvTime.text = item.niceDate
        }
    }

}