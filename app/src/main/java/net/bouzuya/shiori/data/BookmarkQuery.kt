package net.bouzuya.shiori.data

data class BookmarkQuery(private val query: String) {
    fun match(bookmarkWithTagList: BookmarkWithTagList): Boolean =
        query.isEmpty() || bookmarkWithTagList.bookmark.name.contains(query, ignoreCase = true)
}
