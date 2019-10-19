package net.bouzuya.shiori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.BookmarkAction
import net.bouzuya.shiori.data.Tag
import net.bouzuya.shiori.databinding.BookmarkListItemBinding
import net.bouzuya.shiori.databinding.TagListItemBinding

interface OnClickBookmarkListener {
    fun onClick(bookmark: Bookmark)
}

interface OnBookmarkActionListener {
    fun onAction(action: BookmarkAction, bookmark: Bookmark)
}

@BindingAdapter(
    "bookmarkList",
    "onBookmarkActionListener",
    "onClickBookmarkListener",
    "onLongClickBookmarkListener"
)
fun RecyclerView.setBookmarkList(
    bookmarkList: List<Bookmark>?,
    onBookmarkActionListener: OnBookmarkActionListener?,
    onClickBookmarkListener: OnClickBookmarkListener?,
    onLongClickBookmarkListener: OnClickBookmarkListener?
) {
    val itemList = bookmarkList ?: emptyList()

    class BindingViewHolder(val binding: BookmarkListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    adapter = object : RecyclerView.Adapter<BindingViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return BindingViewHolder(
                BookmarkListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            val bookmark = itemList[position]
            holder.binding.bookmark = bookmark
            holder.binding.onClickListener = View.OnClickListener {
                onClickBookmarkListener?.onClick(bookmark)
            }
            holder.binding.onClickMenuListener = View.OnClickListener { v ->
                PopupMenu(context, v).also { popup ->
                    popup.menuInflater.inflate(R.menu.bookmark_popup, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.bookmark_edit -> BookmarkAction.Edit
                            R.id.bookmark_open -> BookmarkAction.Open
                            R.id.bookmark_share -> BookmarkAction.Share
                            else -> null
                        }?.let { action ->
                            onBookmarkActionListener?.onAction(action, bookmark)
                            true
                        } ?: false
                    }
                    popup.show()
                }
            }
            holder.binding.onLongClickListener = View.OnLongClickListener {
                onLongClickBookmarkListener?.onClick(bookmark)
                true
            }
        }
    }
}

interface OnClickTagListener {
    fun onClick(tag: Tag)
}

interface OnLongClickTagListener {
    fun onLongClick(tag: Tag)
}

@BindingAdapter("tagList", "onClickTagListener", "onLongClickTagListener")
fun RecyclerView.setTagList(
    tagList: List<Tag>?,
    onClickTagListener: OnClickTagListener?,
    onLongClickTagListener: OnLongClickTagListener?
) {
    val itemList = tagList ?: emptyList()

    class BindingViewHolder(val binding: TagListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    adapter = object : RecyclerView.Adapter<BindingViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return BindingViewHolder(
                TagListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            holder.binding.tag = itemList[position]
            holder.binding.onClickTagListener = onClickTagListener
            holder.binding.onLongClickTagListener = onLongClickTagListener
        }
    }
}


@BindingAdapter("onLongClick")
fun View.setOnLongClickListenerAdapter(onLongClickListener: View.OnLongClickListener) {
    setOnLongClickListener(onLongClickListener)
}

interface OnLongClickListener {
    fun onLongClick()
}

@BindingAdapter("onLongClick2")
fun View.setMyOnLongClickListener(onLongClickListener: OnLongClickListener) {
    setOnLongClickListener {
        onLongClickListener.onLongClick()
        true
    }
}
