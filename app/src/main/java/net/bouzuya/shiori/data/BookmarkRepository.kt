package net.bouzuya.shiori.data

class BookmarkRepository(private val _bookmarkDao: BookmarkDao) {
    suspend fun countAll(): Int = _bookmarkDao.countAll()

    suspend fun deleteAll(): Unit = _bookmarkDao.deleteAll()

    suspend fun findAll(): List<Bookmark> = _bookmarkDao.findAll()

    suspend fun findById(id: Long): Bookmark? = _bookmarkDao.findById(id).firstOrNull()

    suspend fun insert(bookmark: Bookmark): Unit = _bookmarkDao.insert(bookmark)

    suspend fun update(bookmark: Bookmark): Unit = _bookmarkDao.update(bookmark)
}
