package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

/**
 * Consumes event instances.
 * <p>
 * In addition to the method that consumes an event, a priority along with a
 * topic are required to simplify manipulation by buses.
 * <p>
 * Although this object seems fairly easy to implement, most of the
 * performances optimizations come from it : compilation/JIT inlining, ASM, ...
 * are among all those features that result in major performance results,
 * at either instantiation or invocation.
 *
 * @param <T> the main topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public interface Subscription<T> extends Comparable<Subscription<T>> {
    /**
     * The main topic getter.
     *
     * @return the main topic.
     */
    @NotNull Class<T> topic();

    /**
     * The priority getter.
     * <p>
     * By default, the lower the priority means the later the consumer is
     * called.
     *
     * @return an integer used to sort subscriptions and invoke them in a
     * specific order when an event is published.
     */
    default int priority() {
        return 0;
    }

    /**
     * Consumes the provided event.
     *
     * @param event the event to be consumed.
     */
    default void consume(final @NotNull T event) {
    }

    /**
     * Compares the provided {@link Subscription} to this instance.
     *
     * @param subscription the subscription to be compared.
     * @return an integer, as defined by {@link Comparable#compareTo(Object)}.
     */
    @Override
    default int compareTo(final @NotNull Subscription<T> subscription) {
        return this.priority() - subscription.priority();
    }
}
