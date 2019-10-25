package net.bouzuya.shiori.data

import androidx.room.*

@Dao
interface BookmarkDao {
    @Query("SELECT COUNT(*) FROM bookmarks")
    suspend fun countAll(): Int

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks")
    suspend fun deleteAll()

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    suspend fun findAll(): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun findById(id: Long): List<Bookmark>

    @Insert
    suspend fun insert(bookmark: Bookmark): Long

    @Update
    suspend fun update(bookmark: Bookmark)
}
