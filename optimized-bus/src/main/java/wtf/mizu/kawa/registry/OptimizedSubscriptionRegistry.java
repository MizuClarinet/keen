package wtf.mizu.kawa.registry;

import java.util.Collections;
import java.util.SortedSet;

import wtf.mizu.kawa.api.Subscription;

/**
 * TODO
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class OptimizedSubscriptionRegistry<T> implements SubscriptionRegistry<T> {
    private final SortedSet<Subscription<T>> subscriptions;

    public OptimizedSubscriptionRegistry(
            SortedSet<Subscription<T>> subscriptions
    ) {
        // TODO maybe we want to synchronize stuff here?
        this.subscriptions = subscriptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Subscription<T>> subscriptions() {
        return Collections.unmodifiableSortedSet(subscriptions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        subscriptions.add(subscription);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        subscriptions.remove(subscription);

        if (subscriptions.size() == 1) {
            return new SingletonSubscriptionRegistry<>(subscriptions.first());
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(T event) {
        // This *should* run faster than a for-loop as SortedSet doesn't
        // implement `RandomAccess`.

        subscriptions.forEach((subscription) -> subscription.consume(event));
    }
}
