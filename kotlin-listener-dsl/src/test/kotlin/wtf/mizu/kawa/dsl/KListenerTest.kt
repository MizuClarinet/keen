package wtf.mizu.kawa.dsl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import wtf.mizu.kawa.OptimizedBus

/**
 * Tests the [KListener] class.
 *
 * @author Shyrogan
 * @since 0.0.1
 */
class KListenerTest {
    companion object {
        /**
         * The expected value.
         */
        const val EXPECTED_VALUE = 42
    }

    private val bus = OptimizedBus()

    private var i = 0

    private val listener = listener {
        on<Int> { this@KListenerTest.i += it }
    }

    @Test
    fun creationTest() {
        this.bus.addListener(this.listener)
        this.bus.publish(EXPECTED_VALUE)
        this.bus.removeListener(this.listener)
        this.bus.publish(EXPECTED_VALUE)
        assertEquals(EXPECTED_VALUE, this.i)
    }
}
