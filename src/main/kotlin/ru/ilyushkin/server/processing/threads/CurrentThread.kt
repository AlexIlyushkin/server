package ru.ilyushkin.server.processing.threads

/**
 * Presents current thread
 *
 * @author Alex Ilyushkin
 */
class CurrentThread : Threads {

    /**
     * Runs a runnable provided as parameter through current thread
     */
    override fun run(runnable: Runnable) = runnable.run()
}
