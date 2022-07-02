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
import java.util.List;
import java.util.Map;

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

    @Override
    public <T> void publish(@NotNull T event) {
        final var registry = topicToSubscriptionsMap.get(event.getClass());
        if (registry != null)
            registry.publish(event);
    }

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
    public void addListener(@NotNull Listener listener) {
        for (final var entry : listener.subscriptions().entrySet()) {
            final var topic = entry.getKey();
            final var subscriptions = (List<Subscription<Object>>)
                    ((Object) entry.getValue());

            final var registry = topicToSubscriptionsMap.get(topic);

            if (registry == null) {
                topicToSubscriptionsMap.put(
                        topic,
                        entry.getValue().size() == 1 ?
                                new SingletonSubscriptionRegistry<>(
                                        subscriptions.get(0)
                                ) :
                                new OptimizedSubscriptionRegistry<>(subscriptions)
                );

                return;
            }

            topicToSubscriptionsMap.put(
                    topic,
                    registry.addAll(subscriptions)
            );
        }
    }
}
