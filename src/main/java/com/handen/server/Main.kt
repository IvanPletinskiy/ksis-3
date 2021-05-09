package com.handen.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetSocketAddress


//private val connections = mutableListOf<ConnectionHandler>()
//
//typealias OnMessageReceived = (ConnectionHandler, String) -> Unit
//
////@Throws(Exception::class)
////@JvmStatic
//fun main() {
//
//    // création de la socket
//    val port = 1990
//    val serverSocket = ServerSocket(port)
//    System.err.println("Serveur lancé sur le port : $port")
//
//    // repeatedly wait for connections, and process
//    while (true) {
//
//        // on reste bloqué sur l'attente d'une demande client
//        val clientSocket = serverSocket.accept()
//        System.err.println("Nouveau client connecté")
//
//        // on ouvre un flux de converation
//
////        val `in` = BufferedReader(
////            InputStreamReader(clientSocket.getInputStream(), "utf16")
////        )
//        val out = PrintWriter(
//            BufferedWriter(
//                OutputStreamWriter(clientSocket.getOutputStream())
//            ),
//            true
//        )
//
//        val inputStream = clientSocket.getInputStream()
//        val bufferedInputReader = inputStream.bufferedReader(Charsets.UTF_16)
//        val text = bufferedInputReader.readLines()
//        println(text)
//
//        // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur le flux d'écriture.
//        // la donnée lue est donc retournée exactement au même client.
////        var s: String
////        `in`.lines().forEach {
////            println(it)
//
////            out.write("HTTP/1.0 200 OK\r\n")
////            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n")
////            out.write("Server: Apache/0.8.4\r\n")
////            out.write("Content-Type: text/html\r\n")
////            out.write("Content-Length: 59\r\n")
////            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n")
////            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n")
////            out.write("\r\n")
////            out.write("<TITLE>Exemple</TITLE>")
////            out.write("<P>Ceci est une page d'exemple.</P>")
////        while ((s = `in`.readLine()) != null) {
////            println(s)
////
////
////            out.write("HTTP/1.0 200 OK\r\n")
////            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n")
////            out.write("Server: Apache/0.8.4\r\n")
////            out.write("Content-Type: text/html\r\n")
////            out.write("Content-Length: 59\r\n")
////            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n")
////            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n")
////            out.write("\r\n")
////            out.write("<TITLE>Exemple</TITLE>")
////            out.write("<P>Ceci est une page d'exemple.</P>")
////        }
//
//        // on ferme les flux.
//        System.err.println("Connexion avec le client terminée")
//        out.close()
////    `in`.close()
//        bufferedInputReader.close()
//        clientSocket.close()
//    }
//}
//
//fun main() {
//    val serverSocket = createSocket()
//    val onMessageReceived: OnMessageReceived = { sourceHandler, message ->
//        println(message)
////        connections.filter {
////            it != sourceHandler
////        }.forEach {
////            it.sendMessage(message)
////        }
//    }
//
//    if (serverSocket == null) {
//        println("Can't start socket.")
//        return
//    } else {
//        println("Socket started on port ${serverSocket.localPort}, ${serverSocket.inetAddress}")
//    }
//
//    while (true) {
//        val connectionSocket = serverSocket.accept()
//        connections.add(ConnectionHandler(connectionSocket, onMessageReceived))
//    }
//}
//
//fun createSocket(): ServerSocket? {
//    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
//    println("Enter port number:")
//
//    val portString = bufferedReader.readLine()
//    val port = Integer.parseInt(portString)
//    var socket: ServerSocket? = null
//
//    try {
//        socket = ServerSocket(port)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return socket
//}
//
//class ConnectionHandler(
//    socket: Socket,
//    private val onMessageReceived: OnMessageReceived
//) {
//    private val inputStream = socket.getInputStream()
//    private val bufferedInputReader = inputStream.bufferedReader(Charsets.UTF_8)
//    private val outputStream = socket.getOutputStream()
//    private val bufferedWriter = outputStream.bufferedWriter()
//
//    val inputThread = Thread {
//        while (true) {
//            val input = bufferedInputReader.readLine()
//            println(input)
//        }
//    }.also {
//        it.start()
//    }
//
//    fun sendMessage(line: String) {
//        try {
//            bufferedWriter.write(line + "\n")
//            bufferedWriter.flush()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//}


fun main() {
    val server = HttpServer.create(InetSocketAddress(8000), 0)
    server.createContext("/test", MyHandler())
    server.executor = null // creates a default executor
    server.start()
}

internal class MyHandler : HttpHandler {

    override fun handle(exchange: HttpExchange) {
        println("Got request")
        val response = "This is the response"
        val method = exchange.requestMethod
        when(method) {
            "GET" -> processGet(exchange)
            "PUT" -> processPut(exchange)
            "POST" -> processPost(exchange)
            "DELETE" -> processDelete(exchange)
            "COPY" -> processCopy(exchange)
            "MOVE" -> processMove(exchange)
        }

//        exchange.sendResponseHeaders(200, response.length.toLong())
//        val os = exchange.responseBody
//        os.write(response.toByteArray())
//        os.close()
        val query = exchange.requestURI.query
        val path = query.substring(4)
        val file = File(path)
        val text = file.inputStream().readBytes()
//        val text = "123"
        exchange.sendResponseHeaders(200, text.size.toLong())
        val os = exchange.responseBody
        os.write(text)
        os.close()
    }

    private fun processGet(exchange: HttpExchange) {
//        val query = exchange.requestURI.query
//        val path = query.substring(4)
//        val file = File(path)
//        val text = file.inputStream().bufferedReader().readText()
//        exchange.sendResponseHeaders(200, text.length.toLong())
//        val os = exchange.responseBody
//        os.write(text.toByteArray())
//        os.close()
    }

    private fun processPut(exchange: HttpExchange) {

    }

    private fun processPost(exchange: HttpExchange) {

    }

    private fun processDelete(exchange: HttpExchange) {

    }

    private fun processCopy(exchange: HttpExchange) {

    }

    private fun processMove(exchange: HttpExchange) {

    }
}