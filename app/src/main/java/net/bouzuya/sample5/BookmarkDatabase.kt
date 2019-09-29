package net.bouzuya.sample5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 2)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var database: BookmarkDatabase? = null

        fun getDatabase(context: Context): BookmarkDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                ).addMigrations(BookmarkDatabaseMigration1to2()).build().also { db ->
                    database = db
                }
            }
        }
    }
}
