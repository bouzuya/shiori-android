package net.bouzuya.sample5

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import timber.log.Timber.DebugTree


class Sample5Application : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
