package wtf.mizu.kawa.api;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Associates event topics to their {@link Subscription} {@link Collection}.
 * <p>
 * Supposed to only be for registration purposes, they can be seen as
 * "subscription holders" and are not to be stored by buses.
 * <p>
 * Once again, many ways of representing listeners are possible : classes,
 * instantiated by builders, ...
 *
 * @author Shyrogan
 * @author lambdagg
 * @since 0.0.1
 */
@SuppressWarnings({"unchecked", "unused"})
public interface Listener {
    /**
     * The subscription map getter.
     *
     * @return a {@link Map} that associates each topic type to its
     * {@link Subscription} {@link List}.
     */
    @NotNull Map<Class<?>, Collection<Subscription<?>>> subscriptions();

    /**
     * Gets a subscription list for the given class.
     *
     * @param <T>    the type of subscription to search for.
     * @param tClass the class of the type of subscription to search for.
     * @return the subscription list for Ts, or `null` if none found.
     */
    default <T> Collection<Subscription<? extends T>> subscriptions(
            final @NotNull Class<T> tClass
    ) {
        return (Collection<Subscription<? extends T>>)
                (Object) subscriptions().get(tClass);
    }

    /**
     * Syntactic sugar for {@link #subscriptions(Class)}.
     *
     * @param <T>   the type of subscription to search for.
     * @param dummy hacky way to remove the need of giving a class. No argument
     *              is really needed.
     * @return the subscription collection for Ts, or `null` if none found.
     * @see #subscriptions(Class)
     */
    default <T> Collection<Subscription<? extends T>> subscriptions(
            final T... dummy
    ) {
        return subscriptions(
                (Class<T>) dummy.getClass().getComponentType()
        );
    }
}
