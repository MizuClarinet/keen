package wtf.mizu.keen;

/**
 * A Bus is an object used to publish events and allow subscriptions to consume them.
 */
public interface Bus {

    /**
     * Publishes given event instance to its registered {@link Subscription}s.
     *
     * @param event The event
     * @param <T> The event type
     */
    <T> void publish(T event);

    /**
     * Registers given {@link Subscription} to this bus.
     *
     * @param subscription The {@link Subscription}
     * @param <T> The main topic
     */
    <T> void add(Subscription<T> subscription);

    /**
     * Unregisters given {@link Subscription} to this bus.
     *
     * @param subscription The {@link Subscription}
     * @param <T> The main topic
     */
    <T> void remove(Subscription<T> subscription);

    /**
     * Registers given {@link Subscription} iterable to this bus.
     *
     * @param subscriptions The {@link Subscription} iterable
     */
    default void add(Iterable<? extends Subscription<?>> subscriptions) {
        for(final var sub: subscriptions)
            add(sub);
    }

    /**
     * Unregisters given {@link Subscription} iterable to this bus.
     *
     * @param subscriptions The {@link Subscription} iterable
     */
    default void remove(Iterable<? extends Subscription<?>> subscriptions) {
        for(final var sub: subscriptions)
            add(sub);
    }

    /**
     * Registers given {@link Listener} to this bus.
     *
     * @param listener The {@link Listener}
     */
    default void add(Listener listener)  {
        for(final var subscriptions: listener.getSubscriptions().values()) {
            for(final var sub: subscriptions)
                add(sub);
        }
    }

    /**
     * Unregisters given {@link Listener} to this bus.
     *
     * @param listener The {@link Listener}
     */
    default void remove(Listener listener)  {
        for(final var subscriptions: listener.getSubscriptions().values()) {
            for(final var sub: subscriptions)
                add(sub);
        }
    }

    /**
     * Registers given {@link Listener} iterable to this bus.
     *
     * @param listeners The {@link Listener} iterable
     */
    default void addAll(Iterable<? extends Listener> listeners) {
        for(final var listener: listeners) {
            remove(listener);
        }
    }

    /**
     * Unregisters given {@link Listener} iterable to this bus.
     *
     * @param listeners The {@link Listener} iterable
     */
    default void removeAll(Iterable<? extends Listener> listeners) {
        for(final var listener: listeners) {
            add(listener);
        }
    }

}
