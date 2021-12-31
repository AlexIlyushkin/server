package ru.ilyushkin.server.processing

import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Alex Ilyushkin
 */
class DirectProcessingModelTest {

    @Test
    @DisplayName("Executes runnable provided as parameter")
    fun `execute test`() {
        val runnable = mockk<Runnable> {
            justRun { run() }
        }
        val directProcessingModel = DirectProcessingModel()

        directProcessingModel.execute(runnable)

        verify(exactly = 1) { runnable.run() }
        confirmVerified(runnable)
    }
}
