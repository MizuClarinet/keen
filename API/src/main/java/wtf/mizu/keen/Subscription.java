package wtf.mizu.keen;

import java.util.Set;

/**
 * A {@link Subscription} is an object able to consume a given event instance.
 * In addition to the function that consumes an event, a priority and topic are required to simplify manipulation by buses.
 *
 * <p>
 * Even if this object seems fairly easy to implement, most of the performances optimizations come from it.
 * Compilation inlining, JIT inlining, ASM... All these features result in major performance results, either at instantiation or invocation.
 */
public interface Subscription<T> {

    /**
     * Returns a list of types used to store supported
     * topics.
     *
     * @return The topics
     */
    Set<T> getTopics();

    /**
     * Returns an integer used to sort subscriptions and invoke them in a specific order
     * when an event is published.
     * <p>
     * By default, lower means called later.
     *
     * @return The priority
     */
    default int getPriority() {
        return 0;
    };

    /**
     * Consumes given event.
     *
     * @param event The event
     */
    default void consume(T event) {}

}
