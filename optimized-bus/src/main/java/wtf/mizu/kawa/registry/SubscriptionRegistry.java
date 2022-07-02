package wtf.mizu.kawa.registry;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

import java.util.*;

/**
 * TODO
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public interface SubscriptionRegistry<T> {
    /**
     * The subscription set getter.
     *
     * @return an immutable {@link List} that returns all of the
     * {@link Subscription}s in this {@link SubscriptionRegistry}.
     * Needs to be synchronized if multiple threads access the same
     * registry (Collections.synchronizedList(...)).
     */
    @NotNull List<Subscription<T>> subscriptions();

    /**
     * Adds the given {@link Subscription} to this {@link SubscriptionRegistry}.
     *
     * @param subscription the subscription.
     * @return the new subscription registry.
     */
    @NotNull SubscriptionRegistry<T> add(@NotNull Subscription<T> subscription);

    /**
     * Removes the given {@link Subscription} from this
     * {@link SubscriptionRegistry}.
     *
     * @param subscription the subscription.
     * @return the new subscription registry.
     */
    @NotNull SubscriptionRegistry<T> remove(@NotNull Subscription<T> subscription);

    /**
     * Adds the given {@link Subscription} collection to this
     * {@link SubscriptionRegistry}.
     *
     * @param subscriptions the subscription collection.
     * @return the new subscription registry.
     */
    default @NotNull SubscriptionRegistry<T> addAll(
            @NotNull Collection<? extends Subscription<T>> subscriptions
    ) {
        final var newSize = subscriptions().size() + subscriptions.size();

        if (newSize == 0) {
            return new EmptySubscriptionRegistry<>();
        }

        if (newSize == 1) {
            return new SingletonSubscriptionRegistry<>(
                    subscriptions.isEmpty() ?
                            subscriptions().get(0) :
                            subscriptions.iterator().next()
            );
        }

        final var list = new ArrayList<>(subscriptions());
        list.addAll(subscriptions);
        return new OptimizedSubscriptionRegistry<>(list);
    }

    /**
     * Removes given collection of {@link Subscription} to this
     * {@link SubscriptionRegistry}.
     *
     * @param subscriptions the subscription.
     * @return the new subscription registry.
     */
    default @NotNull SubscriptionRegistry<T> removeAll(
            @NotNull Collection<? extends Subscription<T>> subscriptions
    ) {
        final var list = new ArrayList<>(subscriptions());
        list.removeAll(subscriptions);

        if (list.size() == 0) {
            return new EmptySubscriptionRegistry<>();
        }

        if (list.size() == 1) {
            return new SingletonSubscriptionRegistry<>(list.get(0));
        }

        return new OptimizedSubscriptionRegistry<>(list);
    }

    /**
     * Publishes the given event to its {@link Subscription}s.
     *
     * @param event the event to be published.
     */
    void publish(@NotNull T event);
}
