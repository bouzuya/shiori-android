package net.bouzuya.shiori

class Event<T>(private val value: T) {
    private var _handled = false
    val handled: Boolean
        get(): Boolean = _handled

    fun handle(): T? {
        return if (_handled) null else {
            _handled = true
            value
        }
    }
}
