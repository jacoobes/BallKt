package dev.seren

import dev.seren.Managers.Player


interface Cache<T, V> {

    operator fun set(key: T, value: V)

}


class BallCache<T, V>(maxSize: Int) : Cache<T, V> {

    private val cache: MutableMap<T, V> = object : LinkedHashMap<T, V>(
        0, .75f, true
    ) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<T, V>?): Boolean {
            return size > maxSize
        }

    }

    val size: Int
        get() = cache.size

    infix fun has(value: T): Boolean = cache.containsKey(value)

    fun reset() {
        cache.clear()
    }

    fun dump() {
        println(cache)
    }

    fun size(): Long {
        return synchronized(this) {
            val snapshot = LinkedHashMap(cache)
            snapshot.size.toLong()
        }
    }

    override fun set(key: T, value: V) {
        cache[key] = value
    }

    operator fun get(id: T): V? {
        return cache[id]
    }

    override fun toString(): String = cache.toString()
}