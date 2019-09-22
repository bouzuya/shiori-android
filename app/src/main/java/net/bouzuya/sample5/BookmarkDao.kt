package net.bouzuya.sample5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookmarkDao {
    @Query("DELETE FROM bookmarks")
    suspend fun deleteAll()

    @Query("SELECT * FROM bookmarks")
    suspend fun findAll(): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun findById(id: Long): List<Bookmark>

    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Update
    suspend fun update(bookmark: Bookmark)
}
