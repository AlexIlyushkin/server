package ru.ilyushkin.server.processing.threads.newthreads

import ru.ilyushkin.server.processing.threads.Threads
import java.util.concurrent.ThreadFactory

/**
 * @author Alex Ilyushkin
 */
class NewThreads(
    private val threadFactory: ThreadFactory
) : Threads {

    /**
     * Runs the runnable provided as parameter in new thread created by thread factory
     * @throws ThreadIsNotCreatedException if the thread factory returned null
     */
    override fun run(runnable: Runnable) =
        threadFactory.newThread(runnable)
            .takeIf { it != null }
            .let { it?.start() ?: throw ThreadIsNotCreatedException("A thread has not been created when `newThread(Runnable) method has been called`") }
}
