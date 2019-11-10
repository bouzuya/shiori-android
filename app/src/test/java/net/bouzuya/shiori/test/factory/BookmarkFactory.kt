package net.bouzuya.shiori.test.factory

import net.bouzuya.shiori.data.Bookmark

object BookmarkFactory {
    fun newBookmark(): Bookmark = Bookmark(
        LongFactory.newLong(),
        StringFactory.newName(),
        StringFactory.newUrl(),
        StringFactory.newString(),
        StringFactory.newDateTime()
    )
}
