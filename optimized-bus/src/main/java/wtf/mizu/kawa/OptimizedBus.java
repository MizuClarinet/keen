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
    public <T> void publish(
            final @NotNull T event
    ) {
        final SubscriptionRegistry<Object> registry =
                topicToSubscriptionsMap.get(event.getClass());

        if (registry != null) {
            registry.publish(event);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void addSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Class<T> topic = subscription.topic();
        final SubscriptionRegistry<Object> registry =
                topicToSubscriptionsMap.get(topic);

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
    public <T> void removeSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Class<T> topic = subscription.topic();
        final SubscriptionRegistry<Object> registry =
                topicToSubscriptionsMap.get(topic);

        if (registry != null) {
            final SubscriptionRegistry<Object> newRegistry =
                    registry.remove((Subscription<Object>) subscription);

            if (newRegistry instanceof EmptySubscriptionRegistry<?>) {
                topicToSubscriptionsMap.remove(topic);
            }

            topicToSubscriptionsMap.put(topic, newRegistry);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param listener The {@link Listener}
     */
    @Override
    public void addListener(
            final @NotNull Listener listener
    ) {
        for (final Map.Entry<Class<?>, List<Subscription<?>>> entry :
                listener.subscriptions().entrySet()) {
            final Class<?> topic = entry.getKey();
            final List<Subscription<Object>> subscriptions =
                    (List<Subscription<Object>>) (Object) entry.getValue();

            final SubscriptionRegistry<Object> registry =
                    topicToSubscriptionsMap.get(topic);

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
