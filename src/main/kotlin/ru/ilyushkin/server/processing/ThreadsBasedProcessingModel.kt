package ru.ilyushkin.server.processing

import ru.ilyushkin.server.processing.threads.Threads

/**
 * Presents processing model, that delegates executing to concrete Threads interface implementation
 *
 * @author Alex Ilyushkin
 */
class ThreadsBasedProcessingModel(
    private val threads: Threads
) : ProcessingModel {

    /**
     * Delegates the runnable provided as parameter to Threads implementation
     */
    override fun execute(runnable: Runnable) = threads.run(runnable)
}
