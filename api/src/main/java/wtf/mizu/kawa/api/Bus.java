package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
    <T> CompletableFuture<Void> register(final @NotNull Subscription<T> subscription);

    /**
     * Unregisters the provided {@link Subscription} from this bus.
     *
     * @param <T>          the main topic.
     * @param subscription the {@link Subscription} to unregister.
     */
    <T> CompletableFuture<Void> unregister(final @NotNull Subscription<T> subscription);

    /**
     * Registers the provided {@link Subscription} {@link Collection} to this
     * bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      register.
     */
    default CompletableFuture<Void> registerAll(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        return CompletableFuture.allOf(subscriptions.stream()
                .map(this::register)
                .toArray(CompletableFuture[]::new)
        );
    }

    /**
     * Unregisters the provided {@link Subscription} {@link Collection} from
     * this bus.
     *
     * @param subscriptions the {@link Subscription} {@link Collection} to
     *                      unregister.
     */
    default CompletableFuture<Void> unregisterAll(
            final @NotNull Collection<? extends Subscription<?>> subscriptions
    ) {
        return CompletableFuture.allOf(subscriptions.stream()
                .map(this::register)
                .toArray(CompletableFuture[]::new)
        );
    }

    /**
     * Registers the provided {@link Listener} to this bus.
     *
     * @param listener the {@link Listener} to register.
     */
    default CompletableFuture<Void> registerListener(final @NotNull Listener listener) {
        return CompletableFuture.allOf(listener.subscriptions().values().stream()
                .map(this::registerAll)
                .toArray(CompletableFuture[]::new)
        );
    }

    /**
     * Unregisters the provided {@link Listener} from this bus.
     *
     * @param listener the {@link Listener} to unregister.
     */
    default CompletableFuture<Void> unregisterListener(final @NotNull Listener listener) {
        return CompletableFuture.allOf(listener.subscriptions().values().stream()
                .map(this::unregisterAll)
                .toArray(CompletableFuture[]::new)
        );
    }

    /**
     * Registers the provided {@link Listener} {@link Collection} to this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to register.
     * @return
     */
    default CompletableFuture<Void> registerListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        return CompletableFuture.allOf(listeners.stream()
                .map(this::registerListener)
                .toArray(CompletableFuture[]::new)
        );
    }

    /**
     * Unregisters the provided {@link Listener} collection from this bus.
     *
     * @param listeners the {@link Listener} {@link Collection} to unregister.
     */
    default CompletableFuture<Void> unregisterListeners(
            final @NotNull Collection<? extends Listener> listeners
    ) {
        return CompletableFuture.allOf(listeners.stream()
                .map(this::unregisterListener)
                .toArray(CompletableFuture[]::new)
        );
    }
}
