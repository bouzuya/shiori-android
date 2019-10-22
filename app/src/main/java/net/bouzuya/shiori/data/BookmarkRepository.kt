package net.bouzuya.shiori.data

class BookmarkRepository(
    private val _bookmarkDao: BookmarkDao,
    private val _bookmarkTagJoinDao: BookmarkTagJoinDao
) {
    suspend fun countAll(): Int = _bookmarkDao.countAll()

    suspend fun deleteAll() {
        _bookmarkTagJoinDao.deleteAll()
        _bookmarkDao.deleteAll()
    }

    // TODO: N+1
    suspend fun findAll(): List<BookmarkWithTagList> =
        _bookmarkDao.findAll().map { bookmark ->
            BookmarkWithTagList(bookmark, _bookmarkTagJoinDao.findTagsForBookmark(bookmark.id))
        }

    suspend fun findById(id: Long): BookmarkWithTagList? =
        _bookmarkDao.findById(id).firstOrNull()?.let { bookmark ->
            BookmarkWithTagList(bookmark, _bookmarkTagJoinDao.findTagsForBookmark(bookmark.id))
        }

    suspend fun insert(bookmarkWithTagList: BookmarkWithTagList) {
        _bookmarkDao.insert(bookmarkWithTagList.bookmark)
        bookmarkWithTagList.tagList.forEach { tag ->
            _bookmarkTagJoinDao.insert(BookmarkTagJoin(bookmarkWithTagList.bookmark.id, tag.id))
        }
    }

    suspend fun update(bookmarkWithTagList: BookmarkWithTagList) {
        _bookmarkDao.update(bookmarkWithTagList.bookmark)
        // TODO: when not changed
        _bookmarkTagJoinDao.deleteForBookmark(bookmarkWithTagList.bookmark.id)
        bookmarkWithTagList.tagList.forEach { tag ->
            _bookmarkTagJoinDao.insert(BookmarkTagJoin(bookmarkWithTagList.bookmark.id, tag.id))
        }
    }
}
