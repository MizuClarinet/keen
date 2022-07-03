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

    @Override
    public <T> void addSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Subscription<Object> objSub =
                (Subscription<Object>) subscription;

        topicToSubscriptionsMap.compute(
                subscription.topic(),
                (_topic, registry) ->
                        registry == null ?
                                new SingletonSubscriptionRegistry<>(objSub) :
                                registry.add(objSub)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void removeSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Subscription<Object> objSub =
                (Subscription<Object>) subscription;

        topicToSubscriptionsMap.computeIfPresent(
                subscription.topic(),
                (_clazz, registry) -> {
                    final SubscriptionRegistry<Object> newRegistry =
                            registry.remove(objSub);

                    if (newRegistry instanceof EmptySubscriptionRegistry<?>) {
                        return null;
                    }

                    return newRegistry;
                }
        );
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
            final List<Subscription<Object>> subscriptions =
                    (List<Subscription<Object>>) (Object) entry.getValue();

            topicToSubscriptionsMap.compute(
                    entry.getKey(),
                    (_topic, registry) -> {
                        if (registry != null) {
                            return registry.addAll(subscriptions);
                        }

                        return subscriptions.size() == 1 ?
                                new SingletonSubscriptionRegistry<>(
                                        subscriptions.get(0)
                                ) :
                                new OptimizedSubscriptionRegistry<>(
                                        subscriptions
                                );
                    }
            );
        }
    }
}
