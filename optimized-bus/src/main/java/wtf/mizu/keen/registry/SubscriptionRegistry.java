package wtf.mizu.keen.registry;

import wtf.mizu.keen.api.Subscription;

import java.util.*;

/**
 * TODO
 *
 * @param <T>
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public interface SubscriptionRegistry<T> {
    /**
     * The subscription set getter.
     *
     * @return an immutable {@link SortedSet} that returns all of the
     * {@link Subscription}s in this {@link SubscriptionRegistry}.
     * Needs to be synchronized if multiple threads access the same
     * registry (Collections.synchronizedSortedSet(...)).
     */
    SortedSet<Subscription<T>> subscriptions();

    /**
     * Adds the given {@link Subscription} to this {@link SubscriptionRegistry}.
     *
     * @param subscription the subscription.
     * @return the new subscription registry.
     */
    SubscriptionRegistry<T> add(Subscription<T> subscription);

    /**
     * Removes the given {@link Subscription} from this
     * {@link SubscriptionRegistry}.
     *
     * @param subscription the subscription.
     * @return the new subscription registry.
     */
    SubscriptionRegistry<T> remove(Subscription<T> subscription);

    /**
     * Adds the given {@link Subscription} collection to this
     * {@link SubscriptionRegistry}.
     *
     * @param subscriptions the subscription collection.
     * @return the new subscription registry.
     */
    default SubscriptionRegistry<T> addAll(
            Set<? extends Subscription<T>> subscriptions
    ) {
        final var newSize = subscriptions().size() + subscriptions.size();

        if (newSize == 0) {
            return new EmptySubscriptionRegistry<>();
        }

        if (newSize == 1) {
            return new SingletonSubscriptionRegistry<>(
                    subscriptions.isEmpty() ?
                            subscriptions().first() :
                            subscriptions.iterator().next()
            );
        }

        final var set = new TreeSet<>(subscriptions());
        set.addAll(subscriptions);
        return new OptimizedSubscriptionRegistry<>(set);
    }

    /**
     * Removes given collection of {@link Subscription} to this
     * {@link SubscriptionRegistry}.
     *
     * @param subscriptions the subscription.
     * @return the new subscription registry.
     */
    default SubscriptionRegistry<T> removeAll(
            Set<? extends Subscription<T>> subscriptions
    ) {
        final var set = new TreeSet<>(subscriptions());
        set.removeAll(subscriptions);

        if (set.size() == 0) {
            return new EmptySubscriptionRegistry<>();
        }

        if (set.size() == 1) {
            return new SingletonSubscriptionRegistry<>(set.first());
        }

        return new OptimizedSubscriptionRegistry<>(set);
    }

    /**
     * Publishes the given event to its {@link Subscription}s.
     *
     * @param event the event to be published.
     */
    void publish(T event);
}
