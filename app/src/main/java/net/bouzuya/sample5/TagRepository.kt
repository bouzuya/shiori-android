package net.bouzuya.sample5

class TagRepository(private val _tagDao: TagDao) {
    suspend fun findAll(): List<Tag> = _tagDao.findAll()

    suspend fun findById(id: Long): Tag? = _tagDao.findById(id).firstOrNull()

    suspend fun insert(tag: Tag): Unit = _tagDao.insert(tag)

    suspend fun update(tag: Tag): Unit = _tagDao.update(tag)
}
