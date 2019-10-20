package net.bouzuya.shiori.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkTagJoinDao {

    @Query(
        """
           DELETE FROM bookmarks_tags
           WHERE bookmark_id = :bookmarkId
           """
    )
    suspend fun deleteForBookmark(bookmarkId: Long)

    @Query(
        """
           SELECT tags.id, tags.name, tags.created_at FROM tags
           INNER JOIN bookmarks_tags
           ON tags.id = bookmarks_tags.tag_id
           WHERE bookmarks_tags.bookmark_id = :bookmarkId
           """
    )
    suspend fun findTagsForBookmark(bookmarkId: Long): List<Tag>

    @Insert
    suspend fun insert(bookmarkTagJoin: BookmarkTagJoin)
}
