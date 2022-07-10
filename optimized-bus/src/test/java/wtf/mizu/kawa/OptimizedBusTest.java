package wtf.mizu.kawa;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import wtf.mizu.kawa.api.Bus;
import wtf.mizu.kawa.api.Subscription;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the {@link OptimizedBus} class.
 *
 * @author Shyrogan
 * @since 0.0.1
 */
public class OptimizedBusTest {
    static final int EXPECTED_NUMBER = 42;

    final Bus bus = new OptimizedBus();

    final IncreasingSubscription increasingSubscription = new IncreasingSubscription();

    @Test
    void invocationTest() {
        this.bus.addListener(() ->
                Collections.singletonMap(
                        Integer.class,
                        Collections.singletonList(this.increasingSubscription)
                )
        );

        this.bus.publish(EXPECTED_NUMBER);
        this.bus.publish(EXPECTED_NUMBER);

        assertEquals(EXPECTED_NUMBER * 2, this.increasingSubscription.number);
    }

    static class IncreasingSubscription implements Subscription<Integer> {
        int number;

        @Override
        public @NotNull Class<Integer> topic() {
            return Integer.class;
        }

        @Override
        public void consume(final @NotNull Integer event) {
            this.number += event;
        }
    }
}
