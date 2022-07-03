package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Used to publish events and allow subscriptions to consume them.
 * <p>
 * You can find an example implementation of this class in the
 * {@code wtf.mizu.kawa:optimized-bus} Maven artifact, in the OptimizedBus
 * class.
 *
 * @author Shyrogan
 * @author lambdagg
 * @see <a href="https://github.com/MizuSoftware/kawa/blob/main/optimized-bus/src/main/java/wtf/mizu/kawa/impl/optimized/OptimizedBus.java">example implementation</a>
 * @since 0.0.1
 */
@SuppressWarnings("unused")
public interface Bus {
    /**
     * Publishes the given event instance to its registered
     * {@link Subscription}s.
     *
     * @param event the event.
     * @param <T>   the event type.
     */
    <T> void publish(final @NotNull T event);

    /**
     * Registers the given {@link Subscription} to this bus.
     *
     * @param subscription the {@link Subscription}.
     * @param <T>          the main topic.
     */
    <T> void addSubscription(final @NotNull Subscription<T> subscription);

    /**
     * Unregisters the given {@link Subscription} from this bus.
     *
     * @param subscription the {@link Subscription}.
     * @param <T>          the main topic.
     */
    <T> void removeSubscription(final @NotNull Subscription<T> subscription);

    /**
     * Registers the given {@link Subscription} collection to this bus.
     *
     * @param subscriptions the {@link Subscription} collection.
     */
    default void addSubscriptions(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        for (final Subscription<?> subscription : subscriptions) {
            this.addSubscription(subscription);
        }
    }

    /**
     * Unregisters the given {@link Subscription} collection from this bus.
     *
     * @param subscriptions the {@link Subscription} collection.
     */
    default void removeSubscriptions(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        for (final Subscription<?> subscription : subscriptions) {
            this.removeSubscription(subscription);
        }
    }

    /**
     * Registers the given {@link Listener} to this bus.
     *
     * @param listener the {@link Listener}.
     */
    default void addListener(final @NotNull Listener listener) {
        for (
                final Collection<Subscription<?>> subscriptions :
                listener.subscriptions().values()
        ) {
            this.addSubscriptions(subscriptions);
        }
    }

    /**
     * Unregisters the given {@link Listener} from this bus.
     *
     * @param listener the {@link Listener}.
     */
    default void removeListener(final @NotNull Listener listener) {
        for (
                final Collection<Subscription<?>> subscriptions :
                listener.subscriptions().values()
        ) {
            this.removeSubscriptions(subscriptions);
        }
    }

    /**
     * Registers the given {@link Listener} collection to this bus.
     *
     * @param listeners the {@link Listener} collection.
     */
    default void addListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        for (final Listener listener : listeners) {
            this.addListener(listener);
        }
    }

    /**
     * Unregisters the given {@link Listener} collection from this bus.
     *
     * @param listeners the {@link Listener} collection.
     */
    default void removeListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        for (final Listener listener : listeners) {
            this.removeListener(listener);
        }
    }
}
