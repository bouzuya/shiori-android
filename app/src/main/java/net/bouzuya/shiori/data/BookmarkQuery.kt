package net.bouzuya.shiori.data

data class BookmarkQuery(private val queryString: String) {
    private val predicateList: List<(bookmarkWithTagList: BookmarkWithTagList) -> Boolean> by lazy {
        if (queryString.isEmpty()) emptyList()
        else {
            queryString.split(" ").filter { it.isNotEmpty() }.map { word ->
                { bookmarkWithTagList: BookmarkWithTagList ->
                    word.isEmpty()
                            || bookmarkWithTagList.bookmark.name.contains(word, ignoreCase = true)
                }
            }
        }
    }

    fun match(bookmarkWithTagList: BookmarkWithTagList): Boolean =
        predicateList.isEmpty() || predicateList.all { it(bookmarkWithTagList) }
}
