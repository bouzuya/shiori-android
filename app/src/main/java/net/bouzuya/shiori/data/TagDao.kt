package net.bouzuya.shiori.data

import androidx.room.*

@Dao
interface TagDao {
    @Query("SELECT COUNT(*) FROM tags")
    suspend fun countAll(): Int

    @Delete
    suspend fun delete(tag: Tag)

    @Query("DELETE FROM tags")
    suspend fun deleteAll()

    @Query("SELECT * FROM tags")
    suspend fun findAll(): List<Tag>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun findById(id: Long): List<Tag>

    @Insert
    suspend fun insert(tag: Tag): Long

    @Update
    suspend fun update(tag: Tag)
}
