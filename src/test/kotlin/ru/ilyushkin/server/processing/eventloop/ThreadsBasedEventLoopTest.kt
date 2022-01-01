package ru.ilyushkin.server.processing.eventloop

import io.mockk.*
import kotlinx.coroutines.Runnable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.ilyushkin.server.processing.ProcessingModel
import ru.ilyushkin.server.processing.threads.Threads
import java.util.concurrent.BlockingQueue

/**
 * @author Alex Ilyushkin
 */
@DisplayName("ThreadsBasedEventLoop")
class ThreadsBasedEventLoopTest {

    @Test
    @DisplayName("Starts to take tasks from queue and execute them through the threads")
    fun `start test`() {
        val runnable = mockk<Runnable>()
        val eventQueue = mockk<BlockingQueue<Runnable>> {
            every { take() } returns runnable
        }
        val threads = mockk<Threads> {
            //throw exception to interrupt endless cycle of EventLoop
            justRun { run(runnable) } andThenThrows Exception()
        }

        val threadsBasedEventLoop = ThreadsBasedEventLoop(
            eventQueue = eventQueue,
            threads = threads,
        )
        assertThrows<Exception> { threadsBasedEventLoop.start() }

        verifySequence {
            eventQueue.take()
            threads.run(runnable)
            eventQueue.take()
            threads.run(runnable)
        }
        verify(exactly = 2) { eventQueue.take() }
        verify(exactly = 2) { threads.run(runnable) }
        confirmVerified(runnable, eventQueue, threads)
    }

    @Test
    @DisplayName("Puts the runnable provided as parameter to event queue when `execute(Runnable)` method is invoked")
    fun `execute test`() {
        val runnableTask = mockk<Runnable>()
        val eventQueue = mockk<BlockingQueue<Runnable>> {
            justRun { put(runnableTask) }
        }
        val threads = mockk<Threads>()

        val threadsBasedEventLoop = ThreadsBasedEventLoop(
            eventQueue = eventQueue,
            threads = threads,
        )
        threadsBasedEventLoop.execute(runnableTask)

        verify { eventQueue.put(runnableTask) }
        confirmVerified(runnableTask, eventQueue, threads)
    }
}
