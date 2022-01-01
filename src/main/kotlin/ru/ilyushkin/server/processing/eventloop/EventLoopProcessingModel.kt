package ru.ilyushkin.server.processing.eventloop

import ru.ilyushkin.server.processing.ProcessingModel

/**
 * Processing model realized as Event loop
 * @see <a href="https://en.wikipedia.org/wiki/Event_loop">Event loop Wiki page</a>
 * @author Alex Ilyushkin
 */
interface EventLoopProcessingModel : ProcessingModel {

    /**
     * Starts task executing
     */
    fun start()
}
