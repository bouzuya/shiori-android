package net.bouzuya.shiori.data

data class BookmarkWithTagList(
    val bookmark: Bookmark,
    val tagList: List<Tag>
) {
    fun match(query: String): Boolean = bookmark.name.contains(query, ignoreCase = true)
}
