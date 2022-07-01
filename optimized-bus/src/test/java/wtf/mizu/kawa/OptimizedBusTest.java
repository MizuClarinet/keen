package wtf.mizu.kawa;

import org.junit.jupiter.api.Test;
import wtf.mizu.kawa.api.Bus;
import wtf.mizu.kawa.api.Listener;
import wtf.mizu.kawa.api.Subscription;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class OptimizedBusTest {
    final Bus bus = new OptimizedBus();
    final IncreasingSubscription increasingSubscription = new IncreasingSubscription();

    @Test
    void invocationTest() {
        Listener l = () -> Map.of(Integer.class, List.of(increasingSubscription));
        bus.addListener(l);
        bus.publish(0);
        assumeTrue(increasingSubscription.number == 1);
    }

    static class IncreasingSubscription implements Subscription<Integer> {
        int number;

        @Override
        public Class<Integer> topic() {
            return Integer.class;
        }

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public void consume(Integer event) {
            number++;
        }
    }
}
