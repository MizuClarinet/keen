package wtf.mizu.keen.registry;

import wtf.mizu.keen.Subscription;

public interface SubscriptionRegistry<T> {

    SubscriptionRegistry<T> add(Subscription<T> subscription);

    SubscriptionRegistry<T> remove(Subscription<T> subscription);

    default SubscriptionRegistry<T> add(Iterable<? extends Subscription<T>> iterable) {
        SubscriptionRegistry<T> registry = this;
        for(final var sub: iterable)
            registry = add(sub);

        return registry;
    }

    default SubscriptionRegistry<T> remove(Iterable<? extends Subscription<T>> iterable) {
        SubscriptionRegistry<T> registry = this;
        for(final var sub: iterable)
            registry = remove(sub);

        return registry;
    }

    void publish(T event);

}
