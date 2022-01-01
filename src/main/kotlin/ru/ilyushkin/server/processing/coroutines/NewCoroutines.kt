package ru.ilyushkin.server.processing.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Coroutines interface implementation that executes task through new coroutine
 *
 * @author Alex Ilyushkin
 */
class NewCoroutines(
    private val context: CoroutineContext
) : Coroutines {

    /**
     * Runs through new coroutine a runnable provided as parameter
     *
     * @param runnable
     */
    override suspend fun execute(runnable: Runnable) = coroutineScope {
        launch(context) {
            runnable.run()
        }
        Unit
    }
}
