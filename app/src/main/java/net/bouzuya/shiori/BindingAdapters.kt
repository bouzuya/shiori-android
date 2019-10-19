package net.bouzuya.shiori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.Tag
import net.bouzuya.shiori.databinding.BookmarkListItemBinding
import net.bouzuya.shiori.databinding.TagListItemBinding

interface EditTextAfterTextChangedListener {
    fun afterTextChanged(s: String)
}

@BindingAdapter("editTextAfterTextChanged")
fun EditText.setEditTextAfterTextChanged(listener: EditTextAfterTextChangedListener?) {
    if (listener == null) return
    doAfterTextChanged {
        listener.afterTextChanged(it.toString())
    }
}

interface OnClickBookmarkListener {
    fun onClick(bookmark: Bookmark)
}

@BindingAdapter(
    "bookmarkList",
    "onClickBookmarkListener",
    "onEditBookmarkListener",
    "onLongClickBookmarkListener",
    "onOpenBookmarkListener",
    "onShareBookmarkListener"
)
fun RecyclerView.setBookmarkList(
    bookmarkList: List<Bookmark>?,
    onClickBookmarkListener: OnClickBookmarkListener?,
    onEditBookmarkListener: OnClickBookmarkListener?,
    onLongClickBookmarkListener: OnClickBookmarkListener?,
    onOpenBookmarkListener: OnClickBookmarkListener?,
    onShareBookmarkListener: OnClickBookmarkListener?
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
                            R.id.bookmark_edit -> {
                                onEditBookmarkListener?.onClick(bookmark)
                                true
                            }
                            R.id.bookmark_open -> {
                                onOpenBookmarkListener?.onClick(bookmark)
                                true
                            }
                            R.id.bookmark_share -> {
                                onShareBookmarkListener?.onClick(bookmark)
                                true
                            }
                            else -> false
                        }
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
