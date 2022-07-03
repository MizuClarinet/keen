package wtf.mizu.kawa.registry;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

/**
 * An optimized {@link SubscriptionRegistry}. Turns into a
 * {@link SingletonSubscriptionRegistry} if only one item is left after
 * removal, or into an {@link EmptySubscriptionRegistry} if no item is left ;
 * meaning if a {@link SubscriptionRegistry} object is instance of
 * {@link OptimizedSubscriptionRegistry}, it inevitably has at least 2 items.
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class OptimizedSubscriptionRegistry<T>
        implements SubscriptionRegistry<T> {
    private final @NotNull List<Subscription<T>> subscriptions;

    public OptimizedSubscriptionRegistry(
            final @NotNull List<Subscription<T>> subscriptions
    ) {
        // TODO maybe we want to synchronize stuff here?
        this.subscriptions = subscriptions;
    }

    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }

    @Override
    public @NotNull SubscriptionRegistry<T> add(
            final @NotNull Subscription<T> subscription
    ) {
        if (!subscriptions.contains(subscription)) {
            subscriptions.add(subscription);
            Collections.sort(subscriptions);
        }

        return this;
    }

    @Override
    public @NotNull SubscriptionRegistry<T> remove(
            final @NotNull Subscription<T> subscription
    ) {
        subscriptions.remove(subscription);

        if (subscriptions.size() == 1) {
            return new SingletonSubscriptionRegistry<>(subscriptions.get(0));
        }

        return this;
    }

    @Override
    public void publish(final @NotNull T event) {
        if (subscriptions instanceof RandomAccess) {
            for (int i = 0; i < subscriptions.size(); i++) {
                subscriptions.get(i).consume(event);
            }
        } else {
            for (final Iterator<Subscription<T>> i = subscriptions.iterator(); i.hasNext(); ) {
                i.next().consume(event);
            }
        }
    }
}
