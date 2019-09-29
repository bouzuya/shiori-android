package net.bouzuya.sample5

import org.junit.Assert
import org.junit.Test

class BookmarkTest {
    @Test
    fun testId() {
        val id = 1L
        val bookmark = Bookmark(id, "http://example.com/", "2000-01-02T15:16:17Z")
        Assert.assertEquals(bookmark.id, id)
    }

    @Test
    fun testUrl() {
        val url = "http://example.com/"
        val bookmark = Bookmark(1L, url, "2000-01-02T15:16:17Z")
        Assert.assertEquals(bookmark.url, url)
    }

    @Test
    fun testCreatedAt() {
        val createdAt = "2000-01-02T15:16:17Z"
        val bookmark = Bookmark(1L, "http://example.com/", createdAt)
        Assert.assertEquals(bookmark.createdAt, createdAt)
    }
}
