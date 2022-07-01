package wtf.mizu.kawa;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Bus;
import wtf.mizu.kawa.api.Listener;
import wtf.mizu.kawa.api.Subscription;
import wtf.mizu.kawa.registry.EmptySubscriptionRegistry;
import wtf.mizu.kawa.registry.OptimizedSubscriptionRegistry;
import wtf.mizu.kawa.registry.SingletonSubscriptionRegistry;
import wtf.mizu.kawa.registry.SubscriptionRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

/**
 * TODO
 *
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public class OptimizedBus implements Bus {
    private final Map<Class<?>, SubscriptionRegistry<Object>> topicToSubscriptionsMap =
            new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void publish(@NotNull T event) {
        final var registry = topicToSubscriptionsMap.get(event.getClass());
        if (registry != null)
            registry.publish(event);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void addSubscription(@NotNull Subscription<T> subscription) {
        final var topic = subscription.topic();
        final var registry = topicToSubscriptionsMap.get(topic);

        // TODO maybe we can use compute functions there?

        topicToSubscriptionsMap.put(
                topic,
                registry == null ?
                        new SingletonSubscriptionRegistry<>(
                                (Subscription<Object>) subscription
                        ) :
                        registry.add((Subscription<Object>) subscription)
        );
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void removeSubscription(@NotNull Subscription<T> subscription) {
        final var topic = subscription.topic();
        final var registry = topicToSubscriptionsMap.get(topic);

        if (registry != null) {
            final var removedRegistry = registry.remove(
                    (Subscription<Object>) subscription
            );

            if (removedRegistry instanceof EmptySubscriptionRegistry<?>) {
                topicToSubscriptionsMap.remove(topic);
            }

            topicToSubscriptionsMap.put(topic, removedRegistry);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param listener The {@link Listener}
     */
    @Override
    public <T> void addListener(@NotNull Listener<T> listener) {
        for (final var entry : listener.subscriptions().entrySet()) {
            final var topic = entry.getKey();
            final var subscriptionSet = (SortedSet<Subscription<Object>>)
                    ((Object) entry.getValue());

            final var registry = topicToSubscriptionsMap.get(topic);

            if (registry == null) {
                if (entry.getValue().size() == 1) {
                    topicToSubscriptionsMap.put(
                            topic,
                            new SingletonSubscriptionRegistry<>(
                                    subscriptionSet.first()
                            )
                    );

                    return;
                }

                topicToSubscriptionsMap.put(
                        topic,
                        new OptimizedSubscriptionRegistry<>(subscriptionSet)
                );

                return;
            }

            topicToSubscriptionsMap.put(
                    topic,
                    registry.addAll(subscriptionSet)
            );
        }
    }
}
