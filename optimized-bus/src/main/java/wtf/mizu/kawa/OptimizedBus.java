package wtf.mizu.kawa;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Bus;
import wtf.mizu.kawa.api.Listener;
import wtf.mizu.kawa.api.Subscription;
import wtf.mizu.kawa.registry.EmptySubscriptionRegistry;
import wtf.mizu.kawa.registry.OptimizedSubscriptionRegistry;
import wtf.mizu.kawa.registry.SingletonSubscriptionRegistry;
import wtf.mizu.kawa.registry.SubscriptionRegistry;

import java.util.*;

/**
 * An optimized implementation of the Kawa {@link Bus} interface. Boxes a map
 * of topics->{@link SubscriptionRegistry}, along with common {@link Bus}
 * operations.
 *
 * @author Shyrogan
 * @author lambdagg
 * @see SubscriptionRegistry
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public class OptimizedBus implements Bus {
    private final Map<Class<?>, SubscriptionRegistry<Object>> topicToSubscriptionRegistryMap =
            new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public <T> void publish(
            final @NotNull T event
    ) {
        Optional.ofNullable(
                topicToSubscriptionRegistryMap.get(event.getClass())
        ).ifPresent((registry) -> registry.publish(event));
    }

    /** {@inheritDoc} */
    @Override
    public <T> void addSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Subscription<Object> objSub =
                (Subscription<Object>) subscription;

        topicToSubscriptionRegistryMap.compute(
                subscription.topic(),
                (_topic, registry) ->
                        registry == null ?
                                new SingletonSubscriptionRegistry<>(objSub) :
                                registry.add(objSub)
        );
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void removeSubscription(
            final @NotNull Subscription<T> subscription
    ) {
        final Subscription<Object> objSub =
                (Subscription<Object>) subscription;

        topicToSubscriptionRegistryMap.computeIfPresent(
                subscription.topic(),
                (_clazz, registry) -> {
                    final SubscriptionRegistry<Object> newRegistry =
                            registry.remove(objSub);

                    return (newRegistry instanceof EmptySubscriptionRegistry<?>) ?
                            null :
                            newRegistry;
                }
        );
    }

    /** {@inheritDoc} */
    @Override
    public void addListener(
            final @NotNull Listener listener
    ) {
        for (final Map.Entry<Class<?>, Collection<Subscription<?>>> entry :
                listener.subscriptions().entrySet()) {
            final Collection<Subscription<Object>> subscriptions =
                    (Collection<Subscription<Object>>) (Object) entry.getValue();

            topicToSubscriptionRegistryMap.compute(
                    entry.getKey(),
                    (_topic, registry) -> {
                        if (registry != null) {
                            return registry.addAll(subscriptions);
                        }

                        return subscriptions.size() == 1 ?
                                new SingletonSubscriptionRegistry<>(
                                        subscriptions.iterator().next()
                                ) :
                                new OptimizedSubscriptionRegistry<>(
                                        new ArrayList<>(subscriptions)
                                );
                    }
            );
        }
    }
}
