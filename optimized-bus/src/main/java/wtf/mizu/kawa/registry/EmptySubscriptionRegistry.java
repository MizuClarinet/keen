package wtf.mizu.kawa.registry;

import java.util.Collections;
import java.util.List;

import wtf.mizu.kawa.api.Subscription;

/**
 * TODO
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class EmptySubscriptionRegistry<T> implements SubscriptionRegistry<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription<T>> subscriptions() {
        return Collections.emptyList();
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
