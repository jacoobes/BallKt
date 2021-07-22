package dev.seren

import dev.seren.Managers.Player


interface Cache<T,V> : Map<T, V> {
    override val size: Int

    operator fun set(key: T, value:V)

    override operator fun get(key: T): V?

    fun remove(key: T): V?

    fun clear()
}


class BallCache<T, V> : Cache<T, V> {

    private val cache = HashMap<T, V>()

    override val size: Int
        get() = cache.size


    override fun remove(key: T): V? {
        return cache.remove(key)
    }

    override fun clear() = cache.clear()

    /**
     * Returns a read-only [Set] of all key/value pairs in this map.
     */
    override val entries: MutableSet<MutableMap.MutableEntry<T, V>>
        get() = cache.entries

    /**
     * Returns a read-only [Set] of all keys in this map.
     */
    override val keys: MutableSet<T>
        get() = cache.keys

    /**
     * Returns a read-only [Collection] of all values in this map. Note that this collection may contain duplicate values.
     */
    override val values: MutableCollection<V>
        get() = cache.values

    /**
     * Returns `true` if the map contains the specified [key].
     */
    override fun containsKey(key: T): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns `true` if the map maps one or more keys to the specified [value].
     */
    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in the map.
     */

    /**
     * Returns `true` if the map is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(key: T, value: V) { cache[key] = value }

    override fun get(key: T): V? = cache[key]



}