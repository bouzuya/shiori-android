package net.bouzuya.shiori.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class BookmarkDatabaseMigration3to4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val tableName = "bookmarks_tags"

        database.execSQL("CREATE INDEX IF NOT EXISTS `index_bookmarks_tags_bookmark_id` ON `${tableName}` (`bookmark_id`)")
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_bookmarks_tags_tag_id` ON `${tableName}` (`tag_id`)")
    }
}
