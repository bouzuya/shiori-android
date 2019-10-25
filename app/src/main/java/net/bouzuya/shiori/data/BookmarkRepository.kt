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

    suspend fun deleteById(id: Long) {
        _bookmarkDao.findById(id).firstOrNull()?.let { bookmark ->
            _bookmarkTagJoinDao.deleteForBookmark(bookmark.id)
            _bookmarkDao.delete(bookmark)
        }
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
        val bookmarkId = _bookmarkDao.insert(bookmarkWithTagList.bookmark)
        bookmarkWithTagList.tagList.forEach { tag ->
            _bookmarkTagJoinDao.insert(BookmarkTagJoin(bookmarkId, tag.id))
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
