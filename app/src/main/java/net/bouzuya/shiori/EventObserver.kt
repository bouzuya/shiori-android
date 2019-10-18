package net.bouzuya.shiori

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEvent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        if (event?.handled == false) {
            event.handle()?.let(onEvent)
        }
    }
}
