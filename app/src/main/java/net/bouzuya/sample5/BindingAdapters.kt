package net.bouzuya.sample5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.bouzuya.sample5.databinding.HomeBookmarkListItemBinding

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

    class BindingViewHolder(val binding: HomeBookmarkListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    adapter = object : RecyclerView.Adapter<BindingViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return BindingViewHolder(
                HomeBookmarkListItemBinding.inflate(
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
