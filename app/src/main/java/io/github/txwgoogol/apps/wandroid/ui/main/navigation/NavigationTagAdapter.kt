package io.github.txwgoogol.apps.wandroid.ui.main.navigation

import android.view.LayoutInflater
import android.view.View
import com.ansen.shape.AnsenTextView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.model.bean.Article

class NavigationTagAdapter(articleList: MutableList<Article>) :
    TagAdapter<Article>(articleList) {

    override fun getView(parent: FlowLayout?, position: Int, item: Article): View {
        return LayoutInflater.from(parent?.context).inflate(R.layout.nav_item_tag, parent, false)
            .apply {
                findViewById<AnsenTextView>(R.id.nav_tag).text = item.title
            }
    }

}