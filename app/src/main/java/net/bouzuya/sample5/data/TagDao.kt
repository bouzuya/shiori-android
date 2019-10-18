package net.bouzuya.sample5.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagDao {
    @Query("SELECT COUNT(*) FROM tags")
    suspend fun countAll(): Int

    @Query("SELECT * FROM tags")
    suspend fun findAll(): List<Tag>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun findById(id: Long): List<Tag>

    @Insert
    suspend fun insert(tag: Tag)

    @Update
    suspend fun update(tag: Tag)
}
