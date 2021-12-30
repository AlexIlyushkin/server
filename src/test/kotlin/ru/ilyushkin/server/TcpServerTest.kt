package ru.ilyushkin.server

import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import ru.ilyushkin.server.processing.ProcessingModel
import ru.ilyushkin.server.protocol.Protocol
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

/**
 * @author Alex Ilyushkin
 */
class TcpServerTest {

    @Test
    @DisplayName(
        "Starts to accept incoming TCP connections. " +
                "Provides client socket to `protocol.toRunnable(Socket)` " +
                "and provides resulting runnable to 'processingModel.execute(Runnable)'"
    )
    fun `start test for open server socket`() {
        val clientSocket = mockk<Socket>()
        val serverSocket = mockk<ServerSocket> {
            //throw exception on next call to interrupt endless server loop
            every { accept() } returns clientSocket andThenThrows Exception()
        }
        val protocolRunnable = mockk<Runnable>()
        val protocol = mockk<Protocol> {
            every { toRunnable(clientSocket) } returns protocolRunnable
        }
        val processingModel = mockk<ProcessingModel> {
            justRun { execute(protocolRunnable) }
        }

        val tcpServer = TcpServer(
            serverSocket = serverSocket,
            protocol = protocol,
            processingModel = processingModel
        )
        assertThrows<Exception> { tcpServer.start() }

        verifySequence {
            serverSocket.accept()
            protocol.toRunnable(clientSocket)
            processingModel.execute(protocolRunnable)
            serverSocket.accept()
        }
        confirmVerified(serverSocket, protocol, processingModel)
    }

    @Test
    @DisplayName(
        "Doesn't keep silent about thrown exception " +
                "when `start()` method is executed " +
                "and ServerSocket have been closed"
    )
    fun `start test for closed server socket`() {
        val serverSocket = mockk<ServerSocket> {
            every { accept() } throws SocketException()
        }
        val protocol = mockk<Protocol>()
        val processingModel = mockk<ProcessingModel>()

        val tcpServer = TcpServer(
            serverSocket = serverSocket,
            protocol = protocol,
            processingModel = processingModel
        )
        assertThrows<SocketException> { tcpServer.start() }

        verify(exactly = 1) { serverSocket.accept() }
        confirmVerified(serverSocket, protocol, processingModel)
    }
}
