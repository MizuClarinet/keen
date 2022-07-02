package wtf.mizu.kawa.registry;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

/**
 * TODO
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
    private final Subscription<T> singleton;

    /**
     * The fallback {@link List}, only initialized when calling
     * {@link #subscriptions()}.
     */
    private List<Subscription<T>> fallbackSet = null;

    public SingletonSubscriptionRegistry(Subscription<T> singleton) {
        this.singleton = singleton;
    }

    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        if (fallbackSet == null) {
            fallbackSet = List.of(singleton);
        }

        return fallbackSet;
    }

    @Override
    public @NotNull SubscriptionRegistry<T> add(@NotNull Subscription<T> subscription) {
        final var list = new ArrayList<Subscription<T>>();
        list.add(singleton);
        list.add(subscription);

        return new OptimizedSubscriptionRegistry<>(list);
    }

    @Override
    public @NotNull SubscriptionRegistry<T> remove(@NotNull Subscription<T> subscription) {
        if (singleton.equals(subscription)) {
            return new EmptySubscriptionRegistry<>();
        }

        return this;
    }

    @Override
    public void publish(@NotNull T event) {
        singleton.consume(event);
    }
}
