package wtf.mizu.keen.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Used to publish events and allow subscriptions to consume them.
 * <p>
 * You can find an example implementation of this class in the
 * {@code wtf.mizu.keen:optimized-bus} Maven artifact, in the OptimizedBus
 * class.
 *
 * @author Shyrogan
 * @author lambdagg
 * @see <a href="https://github.com/MizuSoftware/keen/blob/main/optimized-bus/src/main/java/wtf/mizu/keen/impl/optimized/OptimizedBus.java">example implementation</a>
 * @since 0.0.1
 */
public interface Bus {
    /**
     * Publishes the given event instance to its registered
     * {@link Subscription}s.
     *
     * @param event the event.
     * @param <T>   the event type.
     */
    <T> void publish(@NotNull T event);

    /**
     * Registers the given {@link Subscription} to this bus.
     *
     * @param subscription the {@link Subscription}.
     * @param <T>          the main topic.
     */
    <T> void addSubscription(@NotNull Subscription<T> subscription);

    /**
     * Unregisters the given {@link Subscription} from this bus.
     *
     * @param subscription the {@link Subscription}.
     * @param <T>          the main topic.
     */
    <T> void removeSubscription(@NotNull Subscription<T> subscription);

    /**
     * Registers the given {@link Subscription} collection to this bus.
     *
     * @param subscriptions the {@link Subscription} collection.
     */
    default void addSubscriptions(
            @NotNull SortedSet<? extends Subscription<?>> subscriptions
    ) {
        for (final var subscription : subscriptions) {
            this.addSubscription(subscription);
        }
    }

    /**
     * Unregisters the given {@link Subscription} collection from this bus.
     *
     * @param subscriptions the {@link Subscription} collection.
     */
    default void removeSubscriptions(
            @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        for (final var subscription : subscriptions) {
            this.removeSubscription(subscription);
        }
    }

    /**
     * Registers the given {@link Listener} to this bus.
     *
     * @param listener the {@link Listener}.
     */
    default <T> void addListener(@NotNull Listener<T> listener) {
        for (final var subscriptions : listener.subscriptions().values()) {
            this.addSubscriptions(subscriptions);
        }
    }

    /**
     * Unregisters the given {@link Listener} from this bus.
     *
     * @param listener the {@link Listener}.
     */
    default <T> void removeListener(@NotNull Listener<T> listener) {
        for (final var subscriptions : listener.subscriptions().values()) {
            this.removeSubscriptions(subscriptions);
        }
    }

    /**
     * Registers the given {@link Listener} collection to this bus.
     *
     * @param listeners the {@link Listener} collection.
     */
    default void addListeners(
            @NotNull Collection<? extends Listener<?>> listeners
    ) {
        for (final var listener : listeners) {
            this.addListener(listener);
        }
    }

    /**
     * Unregisters the given {@link Listener} collection from this bus.
     *
     * @param listeners the {@link Listener} collection.
     */
    default void removeListeners(
            @NotNull Collection<? extends Listener<?>> listeners
    ) {
        for (final var listener : listeners) {
            this.removeListener(listener);
        }
    }
}
