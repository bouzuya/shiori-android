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
import net.bouzuya.shiori.databinding.BookmarkEditTagListItemBinding
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
    class BindingViewHolder(val binding: BookmarkListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class BookmarkListAdapter : RecyclerView.Adapter<BindingViewHolder>() {
        private var _dataSet: List<Bookmark> = emptyList()
        var dataSet: List<Bookmark>
            get() = _dataSet
            set(values) {
                _dataSet = values
                notifyDataSetChanged()
            }

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
            return dataSet.size
        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            val bookmark = dataSet[position]
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

    if (adapter == null) {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = BookmarkListAdapter()
    }
    val itemList = bookmarkList ?: emptyList()
    (adapter as? BookmarkListAdapter)?.dataSet = itemList
}

@BindingAdapter("bookmarkEditTagList", "bookmarkEditCheckedTagList", "onClickTagCheckListener")
fun RecyclerView.setBookmarkEditTagList(
    tagList: List<Tag>?,
    checkedTagList: List<Tag>?,
    onClickTagCheckListener: OnClickTagListener
) {
    class BindingViewHolder(val binding: BookmarkEditTagListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TagListAdapter : RecyclerView.Adapter<BindingViewHolder>() {
        private var _dataSet: Pair<List<Tag>, List<Tag>> = Pair(emptyList(), emptyList())
        var dataSet: Pair<List<Tag>, List<Tag>>
            get() = _dataSet
            set(values) {
                _dataSet = values
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return BindingViewHolder(
                BookmarkEditTagListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return _dataSet.first.size
        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            val tag = dataSet.first[position]
            holder.binding.tag = tag
            holder.binding.checked = dataSet.second.contains(tag)
            holder.binding.onClick = View.OnClickListener {
                onClickTagCheckListener.onClick(tag)
            }
        }
    }

    if (adapter == null) {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = TagListAdapter()
    }

    val itemList = tagList ?: emptyList()
    val checkedItemList = checkedTagList ?: emptyList()
    (adapter as? TagListAdapter)?.dataSet = Pair(itemList, checkedItemList)
}

interface OnClickTagListener {
    fun onClick(tag: Tag)
}

@BindingAdapter("tagList", "onClickTagListener", "onLongClickTagListener")
fun RecyclerView.setTagList(
    tagList: List<Tag>?,
    onClickTagListener: OnClickTagListener?,
    onLongClickTagListener: OnClickTagListener?
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
            val tag = itemList[position]
            holder.binding.tag = tag
            holder.binding.onClick = View.OnClickListener {
                onClickTagListener?.onClick(tag)
            }
            holder.binding.onLongClick = View.OnLongClickListener {
                onLongClickTagListener?.onClick(tag)
                true
            }
        }
    }
}

@BindingAdapter("onLongClick")
fun View.setOnLongClickListenerAdapter(onLongClickListener: View.OnLongClickListener) {
    setOnLongClickListener(onLongClickListener)
}
