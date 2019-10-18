package net.bouzuya.shiori

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
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

@BindingAdapter("bookmarkList", "onClickBookmarkListener")
fun RecyclerView.setBookmarkList(
    bookmarkList: List<Bookmark>?,
    onClickBookmarkListener: OnClickBookmarkListener?
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
            holder.binding.bookmark = itemList[position]
            holder.binding.onClickBookmarkListener = onClickBookmarkListener
        }
    }
}

interface OnClickTagListener {
    fun onClick(tag: Tag)
}

@BindingAdapter("tagList", "onClickTagListener")
fun RecyclerView.setTagList(
    tagList: List<Tag>?,
    onClickTagListener: OnClickTagListener?
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
        }
    }
}
