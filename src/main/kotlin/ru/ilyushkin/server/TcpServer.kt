package ru.ilyushkin.server

import ru.ilyushkin.server.processing.ProcessingModel
import ru.ilyushkin.server.protocol.Protocol
import java.net.ServerSocket
import java.net.Socket

/**
 * TCP server realization but with configurable application
 * layer protocol and configurable processing model.
 *
 * @author Alex Ilyushkin
 */
class TcpServer(
    private val serverSocket: ServerSocket,
    private val protocol: Protocol,
    private val processingModel: ProcessingModel
) : Server {

    /**
     * Start to accept incoming requests
     */
    override fun start() {
        while (true) {
            val clientSocket: Socket = serverSocket.accept()
            processingModel.execute(
                runnable = protocol.toRunnable(
                    clientSocket = clientSocket
                )
            )
        }
    }
}
