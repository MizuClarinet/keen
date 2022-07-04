package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Publishes events and allows subscriptions to consume them.
 * <p>
 * You can find an example implementation of this class in the
 * {@code wtf.mizu.kawa:optimized-bus} Maven artifact, in the OptimizedBus
 * class.
 * <p>
 * See also: <a href="https://docs.mizu.wtf/kawa/optimized-bus/wtf.mizu.kawa/-optimized-bus">example implementation</a>
 *
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
@SuppressWarnings("unused")
public interface Bus {
    /**
     * Publishes the provided event instance to its registered
     * {@link Subscription}s.
     *
     * @param <T>   the event type.
     * @param event the event to publish.
     */
    <T> void publish(final @NotNull T event);

    /**
     * Registers the provided {@link Subscription} to this bus.
     *
     * @param <T>          the main topic.
     * @param subscription the {@link Subscription} to register.
     */
    <T> void addSubscription(final @NotNull Subscription<T> subscription);

    /**
     * Unregisters the provided {@link Subscription} from this bus.
     *
     * @param <T>          the main topic.
     * @param subscription the {@link Subscription} to unregister.
     */
    <T> void removeSubscription(final @NotNull Subscription<T> subscription);

    /**
     * Registers the provided {@link Subscription} {@link Collection} to this
     * bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      register.
     */
    default void addSubscriptions(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        for (final Subscription<?> subscription : subscriptions) {
            this.addSubscription(subscription);
        }
    }

    /**
     * Unregisters the provided {@link Subscription} {@link Collection} from
     * this bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      unregister.
     */
    default void removeSubscriptions(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        for (final Subscription<?> subscription : subscriptions) {
            this.removeSubscription(subscription);
        }
    }

    /**
     * Registers the provided {@link Listener} to this bus.
     *
     * @param listener the {@link Listener} to register.
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
     * Unregisters the provided {@link Listener} from this bus.
     *
     * @param listener the {@link Listener} to unregister.
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
     * Registers the provided {@link Listener} {@link Collection} to this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to register.
     */
    default void addListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        for (final Listener listener : listeners) {
            this.addListener(listener);
        }
    }

    /**
     * Unregisters the provided {@link Listener} collection from this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to unregister.
     */
    default void removeListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        for (final Listener listener : listeners) {
            this.removeListener(listener);
        }
    }
}
