package ru.ilyushkin.server.processing.coroutines

import io.mockk.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.Runnable

/**
 * @author Alex Ilyushkin
 */
class NewCoroutinesTest {

    @Test
    @DisplayName("Runs through new coroutine created by thread context a runnable provided as parameter")
    fun `execute test`() = runBlocking {
        val runnable = mockk<Runnable> {
            justRun { run() }
        }
        val coroutineContext = newSingleThreadContext("Test coroutine context")

        val newCoroutines = NewCoroutines(
            context = coroutineContext
        )
        newCoroutines.execute(runnable)
        try {
            //Wait for the coroutine job complete
            coroutineContext.job.join()
        } catch (ignore: IllegalStateException) {
            //May be thrown when the coroutine job is already completed. Ignore it.
        }

        verify(exactly = 1) { runnable.run() }
        confirmVerified(runnable)
    }
}
