package keen;

import java.util.List;
import java.util.Map;

/**
 * A {@link Listener} is an object that associates event topics to a list subscriptions.
 * <p>
 * {@link Listener}s are only for registration purposes, they can be seen as "subscription holders" and are not stored by buses.
 * <p>
 * Once again, many way to represent listeners are possible: Classes, instantiated by builders...
 */
public interface Listener {

    /**
     * Returns a {@link Map} that associates each topic type to its {@link Subscription}.
     *
     * @return The subscription map
     */
    Map<Class<?>, List<Subscription<?>>> getSubscriptions();
    
}
