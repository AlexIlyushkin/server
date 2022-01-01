package ru.ilyushkin.server.processing.eventloop

import kotlinx.coroutines.runBlocking
import ru.ilyushkin.server.processing.coroutines.Coroutines
import java.lang.Runnable
import java.util.concurrent.BlockingQueue

/**
 * Event loop that executes queued tasks through coroutines
 *
 * @author Alex Ilyushkin
 */
class CoroutineBasedEventLoop(
    private val eventsQueue: BlockingQueue<Runnable>,
    private val coroutines: Coroutines,
) : EventLoopProcessingModel {

    /**
     * Starts executing of queued tasks.
     * Blocks current thread.
     */
    override fun start() {
        runBlocking {
            while (true) {
                coroutines.execute(eventsQueue.take())
            }
        }
    }

    /**
     * Puts [Runnable] provided as parameter to [BlockingQueue] to dispatch it by event loop
     */
    override fun execute(runnable: Runnable) {
        eventsQueue.put(runnable)
    }
}
