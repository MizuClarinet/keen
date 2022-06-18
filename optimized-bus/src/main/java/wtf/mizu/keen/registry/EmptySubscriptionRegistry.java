package wtf.mizu.keen.registry;

import wtf.mizu.keen.Subscription;

import java.util.Collections;
import java.util.List;

public class EmptySubscriptionRegistry<T> implements SubscriptionRegistry<T> {

    @Override
    public List<Subscription<T>> subscriptions() {
        return Collections.emptyList();
    }

    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        return new SingletonSubscriptionRegistry<>(subscription);
    }

    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        return this;
    }

    @Override
    public void publish(T event) {
        // no op
    }

}
