package wtf.mizu.keen;

import wtf.mizu.keen.registry.EmptySubscriptionRegistry;
import wtf.mizu.keen.registry.SingletonSubscriptionRegistry;
import wtf.mizu.keen.registry.SubscriptionRegistry;

import java.util.HashMap;
import java.util.Map;

public class OptimizedBus implements Bus {

    private final Map<Class<?>, SubscriptionRegistry<Object>> topicToSubscriptions = new HashMap<>();

    /**
     * {@inheritDoc}
     *
     * @param event The event
     * @param <T>
     */
    @Override
    public <T> void publish(T event) {
        final var registry = topicToSubscriptions.get(event.getClass());
        if(registry != null)
            registry.publish(event);
    }

    /**
     * {@inheritDoc}
     *
     * @param subscription The {@link Subscription}
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void add(Subscription<T> subscription) {
        final var registry = topicToSubscriptions.get(subscription.topic());
        if(registry == null) {
            topicToSubscriptions.put(subscription.topic(), new SingletonSubscriptionRegistry<>((Subscription<Object>) subscription));
        } else {
            topicToSubscriptions.put(subscription.topic(), registry.add((Subscription<Object>) subscription));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param subscription The {@link Subscription}
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void remove(Subscription<T> subscription) {
        final var registry = topicToSubscriptions.get(subscription.topic());
        if(registry != null) {
            final var removedRegistry = registry.remove((Subscription<Object>) subscription);
            if(removedRegistry instanceof EmptySubscriptionRegistry<?>) {
                topicToSubscriptions.remove(subscription.topic());
            }
            topicToSubscriptions.put(subscription.topic(), removedRegistry);
        }
    }
}
