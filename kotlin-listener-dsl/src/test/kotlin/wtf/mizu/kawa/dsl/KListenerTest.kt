package wtf.mizu.kawa.dsl

import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

import wtf.mizu.kawa.OptimizedBus

/**
 * Tests the [KListener] class.
 *
 * @author Shyrogan
 * @since 0.0.1
 */
class KListenerTest {
    private val bus = OptimizedBus()

    private var i = 0

    private val listener = listener {
        on<String> { i++ }
    }

    @Test
    fun creationTest() {
        bus.addListener(listener)
        bus.publish(1)
        bus.removeListener(listener)
        bus.publish(1)
        assumeTrue(i == 1)
    }
}
