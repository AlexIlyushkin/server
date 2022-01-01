package ru.ilyushkin.server.processing.eventloop

import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.ilyushkin.server.processing.coroutines.Coroutines
import java.util.concurrent.BlockingQueue

/**
 *
 * @author Alex Ilyushkin
 *
 */
class CoroutineBasedEventLoopTest {

    @Test
    @DisplayName("Starts to take tasks from queue and execute them through coroutines")
    fun `start test`() {
        val runnable = mockk<Runnable>()
        val eventQueue = mockk<BlockingQueue<Runnable>> {
            every { take() } returns runnable
        }
        val coroutines = mockk<Coroutines> {
            //throw an exception to interrupt endless cycle of EventLoop
            coJustRun { execute(runnable) } andThenThrows Exception()
        }

        val coroutineBasedEventLoop = CoroutineBasedEventLoop(
            eventsQueue = eventQueue,
            coroutines = coroutines,
        )
        assertThrows<Exception> { coroutineBasedEventLoop.start() }

        coVerifySequence {
            eventQueue.take()
            coroutines.execute(runnable)
            eventQueue.take()
            coroutines.execute(runnable)
        }
        verify(exactly = 2) { eventQueue.take() }
        coVerify(exactly = 2) { coroutines.execute(runnable) }
        confirmVerified(runnable, eventQueue, coroutines)
    }

    @Test
    @DisplayName("Puts runnable to event queue when `execute()` method is invoked")
    fun `execute test`() {
        val runnable = mockk<Runnable>()
        val eventQueue = mockk<BlockingQueue<Runnable>> {
            justRun { put(runnable) }
        }
        val coroutines = mockk<Coroutines>()

        val coroutineBasedEventLoop = CoroutineBasedEventLoop(
            eventsQueue = eventQueue,
            coroutines = coroutines
        )
        coroutineBasedEventLoop.execute(runnable)

        verify(exactly = 1) { eventQueue.put(runnable) }
        confirmVerified(runnable, eventQueue, coroutines)
    }
}
