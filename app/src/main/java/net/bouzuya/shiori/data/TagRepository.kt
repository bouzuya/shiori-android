package net.bouzuya.shiori.data

class TagRepository(
    private val _bookmarkTagJoinDao: BookmarkTagJoinDao,
    private val _tagDao: TagDao
) {
    suspend fun countAll(): Int = _tagDao.countAll()

    suspend fun deleteAll(): Unit = _tagDao.deleteAll()

    suspend fun deleteById(id: Long) {
        _tagDao.findById(id).firstOrNull()?.let { tag ->
            _bookmarkTagJoinDao.deleteForTag(tag.id)
            _tagDao.delete(tag)
        }
    }

    suspend fun findAll(): List<Tag> = _tagDao.findAll()

    suspend fun findById(id: Long): Tag? = _tagDao.findById(id).firstOrNull()

    suspend fun insert(tag: Tag): Long = _tagDao.insert(tag)

    suspend fun update(tag: Tag): Unit = _tagDao.update(tag)
}
