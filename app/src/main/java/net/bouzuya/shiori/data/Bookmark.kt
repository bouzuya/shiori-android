package net.bouzuya.shiori.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name") // added in database version 2
    val name: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "comment", defaultValue = "") // added in database version 5
    val comment: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String
)
