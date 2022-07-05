package wtf.mizu.kawa.api;

/**
 * Holder class for different values used for {@link Subscription#priority()}.
 *
 * @author xtrm
 * @since 0.3.4
 */
public class Priority {
    /**
     * The lowest priority, will usually be consumed last.
     */
    public static final short LOWEST = -1000;

    /**
     * A low priority, will be consumed a bit later than most other
     * subscriptions.
     */
    public static final short LOW = -500;

    /**
     * The default priority.
     */
    public static final short DEFAULT = 0;

    /**
     * A high priority, will be consumed earlier than most other subscriptions.
     */
    public static final short HIGH = 500;

    /**
     * The highest priority, will ensure its subscription to be consumed first.
     */
    public static final short HIGHEST = 1000;

    /**
     * An array containing the possible default values,
     * ordered from lowest to highest.
     */
    public static final short[] values = {
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
