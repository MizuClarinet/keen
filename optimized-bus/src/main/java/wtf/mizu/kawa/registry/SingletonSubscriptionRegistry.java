package wtf.mizu.kawa.registry;

import java.util.*;
import java.util.function.Consumer;

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
    private final @NotNull List<Subscription<T>> subscriptions;
    private final @NotNull Consumer<T> consumer;

    public SingletonSubscriptionRegistry(
            final @NotNull Subscription<T> singleton
    ) {
        this.subscriptions = Collections.singletonList(singleton);
        this.consumer = singleton.consumer();
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        return subscriptions;
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull SubscriptionRegistry<T> add(
            final @NotNull Subscription<T> subscription
    ) {
        final List<Subscription<T>> list = new ArrayList<>();
        list.add(subscriptions.get(0));
        list.add(subscription);

        return new OptimizedSubscriptionRegistry<>(list);
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull SubscriptionRegistry<T> remove(
            final @NotNull Subscription<T> subscription
    ) {
        if (this.subscriptions.get(0).equals(subscription)) {
            return new EmptySubscriptionRegistry<>();
        }

        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void publish(final @NotNull T event) {
        this.consumer.accept(event);
    }
}
