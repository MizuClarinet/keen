package wtf.mizu.kawa.registry;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import wtf.mizu.kawa.api.Subscription;

/**
 * TODO
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class SingletonSubscriptionRegistry<T>
        implements SubscriptionRegistry<T> {
    /**
     * The singleton {@link Subscription} this registry is using.
     */
    private final Subscription<T> singleton;

    /**
     * The fallback {@link SortedSet}, only initialized when calling
     * {@link #subscriptions()}.
     */
    private SortedSet<Subscription<T>> fallbackSet = null;

    public SingletonSubscriptionRegistry(Subscription<T> singleton) {
        this.singleton = singleton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<Subscription<T>> subscriptions() {
        if (fallbackSet == null) {
            fallbackSet = new TreeSet<>();
            fallbackSet.add(singleton);
            fallbackSet = Collections.unmodifiableSortedSet(fallbackSet);
        }

        return fallbackSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        var set = new TreeSet<Subscription<T>>();
        set.add(singleton);
        set.add(subscription);

        return new OptimizedSubscriptionRegistry<>(set);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        if (singleton.equals(subscription)) {
            return new EmptySubscriptionRegistry<>();
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(T event) {
        singleton.consume(event);
    }
}
