package net.bouzuya.sample5

import android.app.Application
import android.os.StrictMode
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import timber.log.Timber.DebugTree

class Sample5Application : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
            Timber.plant(DebugTree())
        }
    }
}
