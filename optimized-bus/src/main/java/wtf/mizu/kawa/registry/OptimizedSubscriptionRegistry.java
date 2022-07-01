package wtf.mizu.kawa.registry;

import java.util.*;

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
    private final List<Subscription<T>> subscriptions;

    public OptimizedSubscriptionRegistry(
            List<Subscription<T>> subscriptions
    ) {
        // TODO maybe we want to synchronize stuff here?
        this.subscriptions = subscriptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription<T>> subscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        if (!subscriptions.contains(subscription)) {
            subscriptions.add(subscription);
            Collections.sort(subscriptions);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        subscriptions.remove(subscription);

        if (subscriptions.size() == 1) {
            return new SingletonSubscriptionRegistry<>(subscriptions.get(0));
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(T event) {
        if (subscriptions instanceof RandomAccess) {
            for (int i = 0; i < subscriptions.size(); i++) {
                subscriptions.get(i).consume(event);
            }
        } else {
            for (var i = subscriptions.iterator(); i.hasNext(); ) {
                i.next().consume(event);
            }
        }
    }
}
