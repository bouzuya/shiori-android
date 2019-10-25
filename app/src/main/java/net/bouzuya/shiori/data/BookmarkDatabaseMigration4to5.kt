package net.bouzuya.shiori.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class BookmarkDatabaseMigration4to5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE bookmarks ADD COLUMN comment TEXT NOT NULL DEFAULT ''")
    }
}
