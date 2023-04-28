package de.hypercdn.extensions.okhttpktx.proxy.utils

open class LoopIterator<T>(val items: List<T>): Iterator<T> {
    override fun hasNext(): Boolean {
        return items.isNotEmpty()
    }

    private var pos: Int = 0

    @Synchronized
    override fun next(): T {
        try {
            return items[pos++ % items.size]
        } finally {
            if (pos < 0)
                pos = 0
        }
    }

}