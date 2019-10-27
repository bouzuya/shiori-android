package net.bouzuya.shiori.data

data class BookmarkQuery(private val queryString: String) {
    private val predicateList: List<(bookmarkWithTagList: BookmarkWithTagList) -> Boolean> by lazy {
        if (queryString.isEmpty()) emptyList()
        else {
            queryString.split(" ").filter { it.isNotEmpty() }.map { word ->
                if (word.startsWith("tag_id:")) {
                    { bookmarkWithTagList: BookmarkWithTagList ->
                        val tagId = word.substring("tag_id:".length)
                        bookmarkWithTagList.tagList.any { tag -> tag.id.toString() == tagId }
                    }
                } else {
                    { bookmarkWithTagList: BookmarkWithTagList ->
                        word.isEmpty()
                                || bookmarkWithTagList.bookmark.name.contains(
                            word,
                            ignoreCase = true
                        )
                    }
                }
            }
        }
    }

    fun match(bookmarkWithTagList: BookmarkWithTagList): Boolean =
        predicateList.isEmpty() || predicateList.all { it(bookmarkWithTagList) }
}
