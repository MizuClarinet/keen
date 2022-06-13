package wtf.mizu.keen.registry;

import wtf.mizu.keen.Subscription;

import java.util.List;

public class OptimizedSubscriptionRegistry<T> implements SubscriptionRegistry<T> {

    private final List<Subscription<T>> subscriptions;

    public OptimizedSubscriptionRegistry(List<Subscription<T>> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public SubscriptionRegistry<T> add(Subscription<T> subscription) {
        if(!subscriptions.contains(subscription)) {
            subscriptions.add(subscription);
        }
        subscriptions.sort((o1, o2) -> o2.priority() - o1.priority());
        return this;
    }

    @Override
    public SubscriptionRegistry<T> remove(Subscription<T> subscription) {
        subscriptions.remove(subscription);
        if(subscriptions.size() == 1)
            return new SingletonSubscriptionRegistry<>(subscriptions.get(0));
        subscriptions.sort((o1, o2) -> o2.priority() - o1.priority());
        return this;
    }

    @Override
    public void publish(T event) {
        final var size = subscriptions.size();
        for(int i = 0; i < size; i++) {
            subscriptions.get(i).consume(event);
        }
    }
}
