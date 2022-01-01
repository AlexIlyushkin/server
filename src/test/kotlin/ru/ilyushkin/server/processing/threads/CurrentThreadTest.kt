package ru.ilyushkin.server.processing.threads

import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Alex Ilyushkin
 */
@DisplayName("CurrentThread")
class CurrentThreadTest {

    @Test
    @DisplayName("Runs runnable provided as parameter")
    fun `run test`() {
        val runnable = mockk<Runnable> {
            justRun { run() }
        }

        val currentThread = CurrentThread()
        currentThread.run(runnable)

        verify(exactly = 1) { runnable.run() }
        confirmVerified(runnable)
    }
}
