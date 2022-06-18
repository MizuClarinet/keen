package wtf.mizu.keen.registry;

import wtf.mizu.keen.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class SingletonSubscriptionRegistry<T> implements SubscriptionRegistry<T> {

    private final Subscription<T> singleton;

    public SingletonSubscriptionRegistry(Subscription<T> singleton) {
        this.singleton = singleton;
    }

    @Override
    public List<Subscription<T>> subscriptions() {
        return List.of(singleton);
    }

    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        return new OptimizedSubscriptionRegistry<>(new ArrayList<>(asList(singleton, subscription)));
    }

    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        if(singleton.equals(subscription))
            return new EmptySubscriptionRegistry<>();
        return this;
    }

    @Override
    public void publish(T event) {
        singleton.consume(event);
    }

}
