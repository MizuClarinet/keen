package wtf.mizu.kawa.api;

/**
 * Holder class for different values used for {@link Subscription#priority()}.
 *
 * @author xtrm
 * @since 0.3.4
 */
public class Priority {
    /**
     * The lowest priority, will be consumed after everyone else.
     */
    public static final int LOWEST = -1000;

    /**
     * A low priority, will be consumed a bit later than most other
     * subscriptions.
     */
    public static final int LOW = -500;

    /**
     * The default priority.
     */
    public static final int DEFAULT = 0;

    /**
     * A high priority, will be consumed earlier than most other subscriptions.
     */
    public static final int HIGH = 500;

    /**
     * The highest priority, will ensure its subscription to be consumed first.
     */
    public static final int HIGHEST = 1000;

    /**
     * An array containing the possible default values,
     * ordered from lowest to highest.
     */
    public static final int[] values = {
            LOWEST,
            LOW,
            DEFAULT,
            HIGH,
            HIGHEST
    };

    /**
     * Don't instantiate utility-class.
     */
    private Priority() {
    }
}
