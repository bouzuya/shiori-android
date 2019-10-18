package net.bouzuya.sample5.data

class TagRepository(private val _tagDao: TagDao) {
    suspend fun countAll(): Int = _tagDao.countAll()

    suspend fun findAll(): List<Tag> = _tagDao.findAll()

    suspend fun findById(id: Long): Tag? = _tagDao.findById(id).firstOrNull()

    suspend fun insert(tag: Tag): Unit = _tagDao.insert(tag)

    suspend fun update(tag: Tag): Unit = _tagDao.update(tag)
}
