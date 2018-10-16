package channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.selects.select

class BlockingChannel(capacity: Int) : AutoCloseable {
    companion object {
        const val DELAY_MS = 1L
    }

    private val channel = Channel<Unit>(capacity = capacity)

    fun send() = runBlocking {
//        delay(DELAY_MS)
        channel.send(Unit)
    }

    fun receive(): Boolean = runBlocking {
        var msg: Unit? = null
        withTimeoutOrNull(10_000) {
            do {
                select<Unit> {
                    channel.onReceive {
                        msg = it
                    }
                    onTimeout(DELAY_MS) {
                        msg = null
                    }
                }
            } while (msg == null)
        }
        msg != null
    }

    override fun close() {
        channel.close()
    }
}
