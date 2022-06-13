package wtf.mizu.keen

import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

class ListenerDSLTest {

    private var i = 0
    private val bus = OptimizedBus()
    private val listener = listener {
        on<Int> { i++ }
    }

    @Test
    fun creationTest() {
        bus.add(listener)
        bus.publish(1)
        bus.remove(listener)
        bus.publish(1)
        assumeTrue(i == 1)
    }

}