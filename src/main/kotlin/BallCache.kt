package dev.seren



interface Cache<T,V> : MutableMap<T, V> {
    override val size: Int

    operator fun set(key: T, value:V)

    override operator fun get(key: T): V?

    override fun remove(key: T): V?

    override fun clear()
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
    override operator fun get(key: T): V? {
        TODO("Not yet implemented")
    }
    /**
     * Returns `true` if the map is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(key: T, value: V) {
        TODO("Not yet implemented")
    }

    /**
     * Associates the specified [value] with the specified [key] in the map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    override fun put(key: T, value: V): V? = cache.put(key, value)


    /**
     * Updates this map with key/value pairs from the specified map [from].
     */
    override fun putAll(from: Map<out T, V>) = cache.putAll(from)


}