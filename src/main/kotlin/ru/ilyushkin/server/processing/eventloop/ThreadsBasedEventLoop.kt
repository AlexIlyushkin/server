package ru.ilyushkin.server.processing.eventloop

import kotlinx.coroutines.Runnable
import ru.ilyushkin.server.processing.threads.Threads
import java.util.concurrent.BlockingQueue

/**
 * Event loop that executes queued tasks through [Threads]
 *
 * @author Alex Ilyushkin
 */
class ThreadsBasedEventLoop(
    private val eventQueue: BlockingQueue<Runnable>,
    private val threads: Threads,
) : EventLoopProcessingModel {

    /**
     * Starts executing of queued tasks.
     * Blocks current thread.
     */
    override fun start() {
        while (true) {
            threads.run(eventQueue.take())
        }
    }

    /**
     * Puts [Runnable] provided as parameter to [BlockingQueue] to dispatch it by event loop
     */
    override fun execute(runnable: Runnable) {
        eventQueue.put(runnable)
    }
}
