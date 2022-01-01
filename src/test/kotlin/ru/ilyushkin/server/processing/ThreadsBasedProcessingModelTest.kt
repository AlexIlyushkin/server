package ru.ilyushkin.server.processing

import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.ilyushkin.server.processing.threads.Threads

/**
 * @author Alex Ilyushkin
 */
@DisplayName("TreadBasedProcessingModel")
class ThreadsBasedProcessingModelTest {

    @Test
    @DisplayName("Delegates the runnable provided as parameter to Threads interface implementation")
    fun `execute test`() {
        val runnable = mockk<Runnable>()
        val threads = mockk<Threads> {
            justRun { run(runnable = runnable) }
        }

        val threadsBasedProcessingModel = ThreadsBasedProcessingModel(
            threads = threads
        )
        threadsBasedProcessingModel.execute(runnable = runnable)

        verify(exactly = 1) { threads.run(runnable) }
        confirmVerified(runnable, threads)
    }

    @Test
    @DisplayName("Doesn't keep silent when some exception has been thrown by Threads")
    fun `failed execute test`() {
        val runnable = mockk<Runnable>()
        val threads = mockk<Threads> {
            every { run(runnable = runnable) } throws Exception()
        }

        val threadsBasedProcessingModel = ThreadsBasedProcessingModel(
            threads = threads
        )
        assertThrows<Exception> { threadsBasedProcessingModel.execute(runnable = runnable) }
    }
}
