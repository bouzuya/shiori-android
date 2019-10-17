package net.bouzuya.sample5.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// added in version 3
@Entity(
    tableName = "bookmarks_tags",
    primaryKeys = ["bookmark_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = Bookmark::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("bookmark_id")
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tag_id")
        )
    ]
)
data class BookmarkTag(
    @ColumnInfo(name = "bookmark_id")
    val bookmarkId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long
)
