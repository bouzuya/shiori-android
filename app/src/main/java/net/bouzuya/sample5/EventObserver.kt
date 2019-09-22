package net.bouzuya.sample5

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEvent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getValueIfUnhandled()?.let(onEvent)
    }
}
