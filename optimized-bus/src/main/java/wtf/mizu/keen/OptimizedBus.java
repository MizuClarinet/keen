package wtf.mizu.keen;

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
        topicToSubscriptions.get(event.getClass()).publish(event);
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
        for(final var clazz: subscription.topics()) {
            final var registry = topicToSubscriptions.get(clazz);
            if(registry == null) {
                topicToSubscriptions.put(clazz, new SingletonSubscriptionRegistry<>((Subscription<Object>) subscription));
            } else {
                topicToSubscriptions.put(clazz, registry.add((Subscription<Object>) subscription));
            }
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
        for(final var clazz: subscription.topics()) {
            final var registry = topicToSubscriptions.get(clazz);
            if(registry == null) {
                return;
            } else {
                topicToSubscriptions.put(clazz, registry.remove((Subscription<Object>) subscription));
            }
        }
    }
}
