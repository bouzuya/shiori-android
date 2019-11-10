package net.bouzuya.shiori.data

import android.content.Context
import androidx.preference.PreferenceManager

class PreferenceRepository(private val context: Context) {
    fun getBookmarkTapAction(): BookmarkAction {
        // TODO: extract key and defValue
        val value = PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString("bookmark_tap_action", "Open")
        check(value != null)
        return BookmarkAction.valueOf(value)
    }

    fun getBookmarkLongTapAction(): BookmarkAction {
        // TODO: extract key and defValue
        val value = PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString("bookmark_long_tap_action", "Edit")
        check(value != null)
        return BookmarkAction.valueOf(value)
    }
}
