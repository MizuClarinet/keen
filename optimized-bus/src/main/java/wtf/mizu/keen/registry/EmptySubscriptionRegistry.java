package wtf.mizu.keen.registry;

import java.util.Collections;
import java.util.SortedSet;

import wtf.mizu.keen.api.Subscription;

/**
 * TODO
 *
 * @param <T>
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class EmptySubscriptionRegistry<T> implements SubscriptionRegistry<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Subscription<T>> subscriptions() {
        return Collections.emptySortedSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        return new SingletonSubscriptionRegistry<>(subscription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(T event) {
        // No operations need to be done as this registry is *meant* to be
        // empty.
    }
}
