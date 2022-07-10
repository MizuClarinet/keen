package wtf.mizu.kawa.api;

/**
 * Utility class holding default values for {@link Subscription#priority()}.
 *
 * @author xtrm
 * @since 0.3.4
 */
@SuppressWarnings("unused")
public class Priority {
    /**
     * The lowest priority.
     * <p>
     * Subscriptions registered with this {@link Priority} value will be
     * consumed the latter.
     */
    public static final short LOWEST = -1000;

    /**
     * A low priority.
     * <p>
     * Subscriptions registered with this {@link Priority} value will be
     * consumed after most others.
     */
    public static final short LOW = -500;

    /**
     * The default priority.
     * <p>
     * Subscriptions registered with this {@link Priority} value will be
     * consumed at the same time as most others.
     */
    public static final short DEFAULT = 0;

    /**
     * A high priority.
     * <p>
     * Subscriptions registered with this {@link Priority} value will be
     * consumed before most others.
     */
    public static final short HIGH = 500;

    /**
     * The highest priority.
     * <p>
     * Subscriptions registered with this {@link Priority} value will be
     * consumed the earliest.
     */
    public static final short HIGHEST = 1000;

    /**
     * An array containing the possible default values, ordered from lowest to
     * highest.
     */
    public static final short[] defaultValues = {
            LOWEST,
            LOW,
            DEFAULT,
            HIGH,
            HIGHEST,
    };

    /**
     * Don't instantiate utility-class.
     */
    private Priority() {
    }
}
