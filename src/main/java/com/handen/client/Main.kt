package com.handen.client

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.nio.charset.Charset

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        println("Enter one of actions: GET, PUT, POST, DELETE, COPY, MOVE")
        val line = bufferedReader.readLine()
        when (line) {
            "GET" -> processGet(bufferedReader)
            "PUT" -> processPut(bufferedReader)
            "POST" -> processPost(bufferedReader)
            "DELETE" -> processDelete(bufferedReader)
            "COPY" -> processCopy(bufferedReader)
            "MOVE" -> processMove(bufferedReader)
            else -> println("Unknown action")
        }
    }
}

fun processGet(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    sendRequest("GET", "src=$src")
}

fun processPut(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    println("ENTER text:")
    val text = bufferedReader.readLine()
    sendRequest("PUT", "src=$src", "text=$text")
}

fun processPost(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    println("ENTER text:")
    val text = bufferedReader.readLine()
    sendRequest("POST", "src=$src", "text=$text")
}

fun processDelete(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    sendRequest("DELETE", "src=$src")
}

fun processCopy(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    println("ENTER dst:")
    val dst = bufferedReader.readLine()
    sendRequest("COPY", "src=$src", "dst=$dst")
}

fun processMove(bufferedReader: BufferedReader) {
    println("ENTER src:")
    val src = bufferedReader.readLine()
    println("ENTER dst:")
    val dst = bufferedReader.readLine()
    sendRequest("MOVE", "src=$src", "dst=$dst")
}

fun sendRequest(method: String, param1: String, param2: String? = null) {
    val httpClient = HttpClients.createDefault()
    var uri = "http://localhost:8000/files?$param1"
    param2?.let {
        uri += "&$param2"
    }
    val request = RequestBuilder.create(method)
        .setCharset(Charset.forName("UTF-8"))
        .setUri(URI.create(uri)).build()

    val response = httpClient.execute(request)

    handleResponse(response)
}

fun handleResponse(response: CloseableHttpResponse) {
    println("Code: ${response.statusLine.statusCode}")
    if(response.entity.contentLength > 0) {
        val body = response.entity.content.bufferedReader().readText()
        if (body.isNotEmpty()) {
            println(body)
        }
    }
}