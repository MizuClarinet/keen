package wtf.mizu.kawa.api;

import java.util.*;

/**
 * Associates event topics to a {@link Subscription} sorted set.
 * <p>
 * Supposed to only be for registration purposes, they can be seen as
 * "subscription holders" and are not to be stored by buses.
 * <p>
 * Once again, many ways of representing listeners are possible : classes,
 * instantiated by builders, ...
 *
 * @param <T> the main topic.
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
public interface Listener<T> {
    /**
     * The subscription map getter.
     *
     * @return a {@link Map} that associates each topic type to its
     * {@link Subscription} {@link SortedSet}.
     */
    Map<Class<T>, SortedSet<Subscription<T>>> subscriptions();
}
