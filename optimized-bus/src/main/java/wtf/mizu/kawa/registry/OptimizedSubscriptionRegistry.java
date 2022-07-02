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
public class OptimizedSubscriptionRegistry<T> implements SubscriptionRegistry<T> {
    private final List<Subscription<T>> subscriptions;

    public OptimizedSubscriptionRegistry(
            List<Subscription<T>> subscriptions
    ) {
        // TODO maybe we want to synchronize stuff here?
        this.subscriptions = subscriptions;
    }

    @Override
    public @NotNull List<Subscription<T>> subscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }

    @Override
    public @NotNull SubscriptionRegistry<T> add(@NotNull Subscription<T> subscription) {
        if (!subscriptions.contains(subscription)) {
            subscriptions.add(subscription);
            Collections.sort(subscriptions);
        }

        return this;
    }

    @Override
    public @NotNull SubscriptionRegistry<T> remove(@NotNull Subscription<T> subscription) {
        subscriptions.remove(subscription);

        if (subscriptions.size() == 1) {
            return new SingletonSubscriptionRegistry<>(subscriptions.get(0));
        }

        return this;
    }

    @Override
    public void publish(@NotNull T event) {
        if (subscriptions instanceof RandomAccess) {
            for (int i = 0; i < subscriptions.size(); i++) {
                subscriptions.get(i).consume(event);
            }
        } else {
            for (var i = subscriptions.iterator(); i.hasNext(); ) {
                i.next().consume(event);
            }
        }
    }
}
