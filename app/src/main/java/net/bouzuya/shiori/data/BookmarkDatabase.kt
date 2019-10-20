package net.bouzuya.shiori.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Bookmark::class, BookmarkTagJoin::class, Tag::class],
    version = BookmarkDatabase.version
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    abstract fun bookmarkTagJoinDao(): BookmarkTagJoinDao

    abstract fun tagDao(): TagDao

    companion object {
        const val version = 4

        @Volatile
        private var database: BookmarkDatabase? = null

        fun getDatabase(context: Context): BookmarkDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                )
                    .addMigrations(BookmarkDatabaseMigration1to2())
                    .addMigrations(BookmarkDatabaseMigration2to3())
                    .addMigrations(BookmarkDatabaseMigration3to4())
                    .build().also { db ->
                        database = db
                    }
            }
        }
    }
}
