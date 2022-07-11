package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Publishes events and allows subscriptions to consume them.
 * <p>
 * You can find an example implementation of this class in the
 * {@code wtf.mizu.kawa:optimized-bus} Maven artifact, in the OptimizedBus
 * class.
 * <p>
 * See also:
 * <a href="https://docs.mizu.wtf/kawa/optimized-bus/wtf.mizu.kawa/-optimized-bus">
 *     example implementation
 * </a>
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
     * @return TODO
     */
    <T> CompletableFuture<Void> register(
            final @NotNull Subscription<T> subscription
    );

    /**
     * Unregisters the provided {@link Subscription} from this bus.
     *
     * @param <T>          the main topic.
     * @param subscription the {@link Subscription} to unregister.
     * @return TODO
     */
    <T> CompletableFuture<Void> unregister(
            final @NotNull Subscription<T> subscription
    );

    /**
     * Registers the provided {@link Subscription} {@link Collection} to this
     * bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      register.
     * @return TODO
     */
    default CompletableFuture<Void> registerAll(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        return futureAllOfMap(subscriptions, this::register);
    }

    /**
     * Unregisters the provided {@link Subscription} {@link Collection} from
     * this bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      unregister.
     * @return TODO
     */
    default CompletableFuture<Void> unregisterAll(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        return futureAllOfMap(subscriptions, this::register);
    }

    /**
     * Registers the provided {@link Listener} to this bus.
     *
     * @param listener the {@link Listener} to register.
     * @return TODO
     */
    default CompletableFuture<Void> registerListener(
            final @NotNull Listener listener
    ) {
        return futureAllOfMap(
                listener.subscriptions().values(),
                this::registerAll
        );
    }

    /**
     * Unregisters the provided {@link Listener} from this bus.
     *
     * @param listener the {@link Listener} to unregister.
     * @return TODO
     */
    default CompletableFuture<Void> unregisterListener(
            final @NotNull Listener listener
    ) {
        return futureAllOfMap(
                listener.subscriptions().values(),
                this::unregisterAll
        );
    }

    /**
     * Registers the provided {@link Listener} {@link Collection} to this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to register.
     * @return TODO
     */
    default CompletableFuture<Void> registerListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        return futureAllOfMap(listeners, this::registerListener);
    }

    /**
     * Unregisters the provided {@link Listener} collection from this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to unregister.
     * @return TODO
     */
    default CompletableFuture<Void> unregisterListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        return futureAllOfMap(listeners, this::unregisterListener);
    }

    @SuppressWarnings("unchecked")
    static <T> CompletableFuture<Void> futureAllOfMap(
            Collection<T> collection,
            Function<? super T, ? extends CompletableFuture<Void>> mapper
    ) {
        return CompletableFuture.allOf(
                (CompletableFuture<Void>[]) collection.stream()
                        .map(mapper)
                        .toArray(Object[]::new)
        );
    }
}
