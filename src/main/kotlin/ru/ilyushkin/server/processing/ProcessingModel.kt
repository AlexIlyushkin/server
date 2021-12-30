package ru.ilyushkin.server.processing

import kotlinx.coroutines.Runnable

/**
 * The interface is an abstraction over concrete request processing model
 * (such as Sequential processing, Process per request, Thread per request and i.e.)
 *
 * @author Alex Ilyushkin
 */
interface ProcessingModel {

    /**
     * Runs request handling
     */
    fun execute(runnable: Runnable)
}
