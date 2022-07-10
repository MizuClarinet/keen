package wtf.mizu.kawa.util;

import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Consumer;

/**
 * Utility methods for optimized iterating over lists.
 *
 * @author lambdagg
 * @since 0.4.0
 */
public class ListIterationUtil {
    /**
     * Don't instantiate utility-class.
     */
    private ListIterationUtil() {
    }

    /**
     * Some {@link List} implementations (like {@link java.util.ArrayList}) also
     * implement RandomAccess, which benchmarks proved to be faster iterating
     * over using a for-i loop and {@link List#get(int)}. However,
     * non-random-access lists have a better operation/second score using
     * {@link List#iterator()} while iterating.
     *
     * @param <T> the list item type.
     * @param list the list to be iterated over.
     * @param consumer the consumer to apply on each iteration.
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static <T> void optimizedIterationThrough(
            List<T> list,
            Consumer<T> consumer
    ) {
        if (list instanceof RandomAccess) {
            for (int i = 0; i < list.size(); i++) {
                consumer.accept(list.get(i));
            }
        } else {
            for (final Iterator<T> i = list.iterator(); i.hasNext(); ) {
                consumer.accept(i.next());
            }
        }
    }
}
