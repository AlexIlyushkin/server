package ru.ilyushkin.server.processing

/**
 * Processing model implementation, that processes requests in the same thread
 *
 * @author Alex Ilyushkin
 */
class DirectProcessingModel : ProcessingModel {

    /**
     * Executes runnable in the same thread
     */
    override fun execute(runnable: Runnable) = runnable.run()
}
