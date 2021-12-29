package ru.ilyushkin.server.protocol

import java.net.Socket

/**
 * Abstraction over concrete application layer protocol
 *
 * @author Alex Ilyushkin
 */
interface Protocol {

    /**
     * @param clientSocket - socket accepted by ServerSocket
     * @return Runnable that handles clientSocket
     */
    fun toRunnable(clientSocket: Socket): Runnable
}
