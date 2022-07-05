package wtf.mizu.kawa.registry;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

/**
 * An empty {@link SubscriptionRegistry}. Turns into a
 * {@link SingletonSubscriptionRegistry} if an item gets added.
 *
 * @param <T> the topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public class EmptySubscriptionRegistry<T> implements SubscriptionRegistry<T> {
    /** {@inheritDoc} */
    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull SubscriptionRegistry<T> add(
            final @NotNull Subscription<T> subscription
    ) {
        return new SingletonSubscriptionRegistry<>(subscription);
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull SubscriptionRegistry<T> remove(
            final @NotNull Subscription<T> subscription
    ) {
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void publish(final @NotNull T event) {
        // No operations need to be done as this registry is *meant* to be
        // empty.
    }
}
