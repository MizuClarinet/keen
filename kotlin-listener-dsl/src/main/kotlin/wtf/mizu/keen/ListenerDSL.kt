package wtf.mizu.keen

class ListenerDSL: Listener {
    private val subscriptions = mutableMapOf<Class<*>, MutableList<Subscription<*>>>()
    override fun subscriptions() = subscriptions
}

inline fun listener(crossinline block: ListenerDSL.() -> Unit)
        = ListenerDSL().apply(block)

inline fun <reified T: Any> Listener.on(crossinline block: T.() -> Unit) = object: Subscription<T> {
    override fun topics() = T::class.java.superclasses
    override fun priority() = 0
    override fun consume(event: T) {
        block(event)
    }
}.also { subscription ->
    subscription.topics().forEach { topic ->
        subscriptions()
            .getOrPut(topic) { mutableListOf() }
            .add(subscription)
    }
}