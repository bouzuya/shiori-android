package net.bouzuya.sample5.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class BookmarkDatabaseMigration1to2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE bookmarks ADD COLUMN name TEXT NOT NULL DEFAULT ''")
    }
}
