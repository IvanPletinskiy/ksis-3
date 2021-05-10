package com.handen.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetSocketAddress
import java.util.concurrent.Executors

fun main() {
    val server = HttpServer.create(InetSocketAddress(8000), 0)
    server.createContext("/files", Handler())
    server.executor = Executors.newFixedThreadPool(2) // creates a default executor
    server.start()
}

class Handler : HttpHandler {

    override fun handle(exchange: HttpExchange) {
        println("Got request")

        val args = exchange.requestURI.query.split("&")

        when (exchange.requestMethod) {
            "GET" -> processGet(exchange, args)
            "PUT" -> processPut(exchange, args)
            "POST" -> processPost(exchange, args)
            "DELETE" -> processDelete(exchange, args)
            "COPY" -> processCopy(exchange, args)
            "MOVE" -> processMove(exchange, args)
        }
    }

    private fun processGet(exchange: HttpExchange, args: List<String>) {
        try {
            val path = args[0].substring(4)
            val file = File(path)
            if(!file.exists()) {
                send404(exchange)
                return
            }
            val text = file.inputStream().bufferedReader().readText()
            exchange.sendResponseHeaders(200, text.length.toLong())
            val os = exchange.responseBody
            os.write(text.toByteArray())
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun processPut(exchange: HttpExchange, args: List<String>) {
        try {
            val path = args[0].substring(4)
            val text = args[1].substring(5)
            val file = File(path)
            if(!file.exists()) {
                send404(exchange)
                return
            }

            file.writeBytes(text.toByteArray())
            exchange.sendResponseHeaders(200, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun processPost(exchange: HttpExchange, args: List<String>) {
        try {
            val path = args[0].substring(4)
            val text = args[1].substring(5)
            val file = File(path)
            if(!file.exists()) {
                send404(exchange)
                return
            }

            file.appendBytes(text.toByteArray())
            exchange.sendResponseHeaders(200, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun processDelete(
        exchange: HttpExchange,
        args: List<String>
    ) {
        try {
            val path = args[0].substring(4)
            val file = File(path)
            if(!file.exists()) {
                send404(exchange)
                return
            }
            val result = file.delete()
            if (result) {
                exchange.sendResponseHeaders(200, 0)
            } else {
                exchange.sendResponseHeaders(500, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun processCopy(exchange: HttpExchange, args: List<String>) {
        try {
            val path = args[0].substring(4)
            val srcFile = File(path)
            val path2 = args[1].substring(4)
            val dstFile = File(path2)

            if(!srcFile.exists()) {
                send404(exchange)
                return
            }

            val result = srcFile.copyTo(dstFile, true)

            if (result.exists()) {
                exchange.sendResponseHeaders(200, 0)
            } else {
                exchange.sendResponseHeaders(500, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun processMove(exchange: HttpExchange, args: List<String>) {
        try {
            val path = args[0].substring(4)
            val srcFile = File(path)
            val path2 = args[1].substring(4)
            val dstFile = File(path2)

            val result = srcFile.copyTo(dstFile, true)
            if(!srcFile.exists()) {
                send404(exchange)
                return
            }

            if (result.exists()) {
                exchange.sendResponseHeaders(200, 0)
            } else {
                exchange.sendResponseHeaders(500, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            exchange.sendResponseHeaders(500, 0)
        }
    }

    private fun send404(exchange: HttpExchange) {
        val message = "File not found"
        exchange.sendResponseHeaders(404, message.length.toLong())
        val os = exchange.responseBody
        os.write(message.toByteArray())
        os.close()
    }
}