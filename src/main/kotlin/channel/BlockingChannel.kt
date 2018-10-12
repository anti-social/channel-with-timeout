package channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class BlockingChannel(capacity: Int) : AutoCloseable {
    companion object {
        const val DELAY_MS = 1L
    }

    private val channel = Channel<Unit>(capacity = capacity)

    fun send() = runBlocking {
        // delay(DELAY_MS)
        channel.send(Unit)
    }

    fun receive(): Boolean = runBlocking {
        val msg = withTimeoutOrNull(10_000) {
            // This works fine
            // channel.receive()

            var msg: Unit?
            do {
                msg = withTimeoutOrNull(DELAY_MS) {
                    channel.receive()
                }
            } while (msg == null)
            msg
        }
        msg != null
    }

    override fun close() {
        channel.close()
    }
}
