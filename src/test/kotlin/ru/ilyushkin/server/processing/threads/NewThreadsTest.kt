package ru.ilyushkin.server.processing.threads

import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.ilyushkin.server.processing.threads.newthreads.NewThreads
import ru.ilyushkin.server.processing.threads.newthreads.ThreadIsNotCreatedException
import java.util.concurrent.ThreadFactory

/**
 * @author Alex Ilyushkin
 */
@DisplayName("NewTreads")
class NewThreadsTest {

    @Test
    @DisplayName("Creates a thread by ThreadFactory providing runnable as parameter to `newThread(Runnable)` method and starts created thread")
    fun `run test`() {
        val runnable = mockk<Runnable>()
        val thread = mockk<Thread> {
            justRun { start() }
        }
        val threadFactory = mockk<ThreadFactory> {
            every { newThread(runnable) } returns thread
        }

        val newThreads = NewThreads(
            threadFactory = threadFactory
        )
        newThreads.run(runnable = runnable)

        verify(exactly = 1) { threadFactory.newThread(runnable) }
        verify(exactly = 1) { thread.start() }
        confirmVerified(runnable, thread, threadFactory)
    }

    @Test
    @DisplayName("Throws ThreadIsNotCreatedException if ThreadFactory returned null")
    fun `run test for thread factory returning null instead of new thread`() {
        val runnable = mockk<Runnable>()
        val threadFactory = mockk<ThreadFactory> {
            every { newThread(runnable) } returns null
        }

        val newThreads = NewThreads(
            threadFactory = threadFactory
        )
        assertThrows<ThreadIsNotCreatedException> { newThreads.run(runnable = runnable) }
    }
}
