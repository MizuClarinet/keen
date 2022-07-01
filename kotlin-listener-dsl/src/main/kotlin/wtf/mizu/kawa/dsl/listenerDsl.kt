package wtf.mizu.kawa.dsl

import wtf.mizu.kawa.api.Listener
import wtf.mizu.kawa.api.Subscription

/**
 * A Kotlin subclass of the original [Listener] class, used by DSL inline
 * functions as defined below.
 *
 * @author Shyrogan
 * @author lambdagg
 * @see Listener
 * @since 0.0.1
 */
open class KListener : Listener {
    private val subscriptions =
        mutableMapOf<Class<*>, List<Subscription<*>>>()

    override fun subscriptions() = subscriptions
}

/**
 * Creates an inlined listener defined by the given block.
 *
 * @property block the block to apply to the [KListener].
 *
 * @return the newly created [KListener].
 */
inline fun listener(crossinline block: KListener.() -> Unit) =
    KListener().apply(block)

/**
 * Creates an inlined event subscription defined by the given block.
 *
 * @param T the main topic.
 * @property priority the subscription priority. Defaults to 0.
 * @property block the block to use as the [Subscription] consumer.
 *
 * @return the newly created subscription.
 */
inline fun <reified T : Any> Listener.on(
    priority: Int = 0,
    crossinline block: (T) -> Unit,
) = object : Subscription<T> {
    override fun topic() = T::class.java

    override fun priority() = priority

    override fun consume(event: T) = block(event)
}.also { subscription ->
    subscriptions()
        .getOrPut(subscription.topic()) { mutableListOf() }
        .add(subscription)
}
