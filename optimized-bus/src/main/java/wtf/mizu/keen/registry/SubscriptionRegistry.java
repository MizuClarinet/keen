package wtf.mizu.keen.registry;

import wtf.mizu.keen.Subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface SubscriptionRegistry<T> {

    /**
     * An immutable {@link List} that returns all the {@link Subscription}
     * contained by this {@link SubscriptionRegistry}.
     *
     * @return The subscriptions
     */
    List<Subscription<T>> subscriptions();

    /**
     * Adds given {@link Subscription} to this {@link SubscriptionRegistry}.
     *
     * @param subscription The subscription
     * @return The new subscription registry
     */
    SubscriptionRegistry<T> add(Subscription<T> subscription);

    /**
     * Removes given {@link Subscription} from this {@link SubscriptionRegistry}.
     *
     * @param subscription The subscription
     * @return The new subscription registry
     */
    SubscriptionRegistry<T> remove(Subscription<T> subscription);

    /**
     * Adds given collection of {@link Subscription} to this {@link SubscriptionRegistry}.
     *
     * @param subscriptions The subscription
     * @return The new subscription registry
     */
    default SubscriptionRegistry<T> add(Collection<? extends Subscription<T>> subscriptions) {
        final var finalSize = subscriptions().size() + subscriptions.size();
        if(finalSize == 0)
            return new EmptySubscriptionRegistry<>();
        if(finalSize == 1)
            return new SingletonSubscriptionRegistry<>(subscriptions.isEmpty() ? subscriptions().get(0) : subscriptions.iterator().next());
        final var list = new ArrayList<>(subscriptions());
        list.addAll(subscriptions);
        return new OptimizedSubscriptionRegistry<>(list);
    }

    /**
     * Removes given collection of {@link Subscription} to this {@link SubscriptionRegistry}.
     *
     * @param subscriptions The subscription
     * @return The new subscription registry
     */
    default SubscriptionRegistry<T> remove(Collection<? extends Subscription<T>> subscriptions) {
        final var list = new ArrayList<>(subscriptions());
        list.removeAll(subscriptions);

        if(list.size() == 0)
            return new EmptySubscriptionRegistry<>();
        if(list.size() == 1) {
            return new SingletonSubscriptionRegistry<>(list.get(0));
        }
        return new OptimizedSubscriptionRegistry<>(list);
    }

    /**
     * Publishes given {@link T} event to its {@link Subscription}.
     *
     * @param event The event
     */
    void publish(T event);


}