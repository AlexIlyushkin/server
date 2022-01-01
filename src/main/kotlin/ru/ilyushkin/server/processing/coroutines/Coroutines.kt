package ru.ilyushkin.server.processing.coroutines

/**
 * Presents a set of coroutines
 *
 * @author Alex Ilyushkin
 */
interface Coroutines {

    /**
     * Runs through coroutine a runnable provided as parameter
     * @param runnable
     */
    suspend fun execute(runnable: Runnable)
}
