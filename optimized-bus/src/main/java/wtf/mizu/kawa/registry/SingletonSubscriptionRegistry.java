package wtf.mizu.kawa.registry;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

/**
 * A subscription registry that strictly holds a single non-null element. Turns
 * into an {@link EmptySubscriptionRegistry} if the only present item is
 * removed, or into an {@link OptimizedSubscriptionRegistry} if other items are
 * added.
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class SingletonSubscriptionRegistry<T>
        implements SubscriptionRegistry<T> {
    /**
     * The singleton {@link Subscription} this registry is using.
     */
    private final @NotNull Subscription<T> singleton;

    /**
     * The fallback singleton {@link List}, only initialized when calling
     * {@link #subscriptions()}.
     */
    private List<Subscription<T>> fallbackList = null;

    public SingletonSubscriptionRegistry(
            final @NotNull Subscription<T> singleton
    ) {
        this.singleton = singleton;
    }

    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        return fallbackList == null ?
                fallbackList = Collections.singletonList(singleton) :
                fallbackList;
    }

    @Override
    public @NotNull SubscriptionRegistry<T> add(
            final @NotNull Subscription<T> subscription
    ) {
        final List<Subscription<T>> list = new ArrayList<>();
        list.add(singleton);
        list.add(subscription);

        return new OptimizedSubscriptionRegistry<>(list);
    }

    @Override
    public @NotNull SubscriptionRegistry<T> remove(
            final @NotNull Subscription<T> subscription
    ) {
        if (singleton.equals(subscription)) {
            return new EmptySubscriptionRegistry<>();
        }

        return this;
    }

    @Override
    public void publish(final @NotNull T event) {
        singleton.consume(event);
    }
}
