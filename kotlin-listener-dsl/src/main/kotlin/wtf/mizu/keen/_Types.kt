package wtf.mizu.keen

/**
 * A type alias
 */
typealias Topic<T> = Class<T>

val <T: Any> Class<T>.superclasses: Set<Topic<in T>>
    get() {
        val result = mutableSetOf<Topic<in T>>()
        var parent = this as Topic<in T>
        while (parent != Object::class.java || parent.superclass != Object::class.java) {
            result.add(parent)
            parent = (parent.superclass as Topic<in T>)
        }
        return result
    }