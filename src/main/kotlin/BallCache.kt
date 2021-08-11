package dev.seren

import dev.seren.Managers.Player
import dev.seren.serializables.player.PlayerData


interface Cache<T, V> {

    operator fun set(key: T, value: V)

}

@Suppress("UNCHECKED_CAST")
class BallCache<T, V>(maxSize: Int) : Cache<T, V> {

    private val cache: MutableMap<T, V> = object : LinkedHashMap<T, V>(
        0, .75f, true
    ) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<T, V>?): Boolean {
            return size > maxSize
        }

    }

    val values : MutableCollection<V>
      get() = cache.values

    val size: Int
        get() = cache.size

    infix fun hasKey(value: T): Boolean = cache.containsKey(value)

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

    operator fun get(id: T): V {
        return cache[id]!!
    }

    inline fun find( condition : (V) -> Boolean ) : V? {
        return values.find(condition)
    }

    override fun toString(): String = cache.toString()
}