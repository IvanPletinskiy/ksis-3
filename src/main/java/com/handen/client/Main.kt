package com.handen.client

import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients
import java.net.URI
import java.nio.charset.Charset

fun main() {
    val fooUrl = "https://0.0.0.0:1990/files?src=C:/ksis/src.txt&dst=C:/ksis/dst.txt"
    val httpClient = HttpClients.createDefault()
//    val request = RequestBuilder.create("COPY").setCharset(Charsets.UTF_16).setUri(URI.create("https://0.0.0.0:1990/files?src=C:/ksis/src.txt&dst=C:/ksis/dst.txt")).build()
//    val request = RequestBuilder.create("COPY").setCharset(Charsets.UTF_16).setUri(URI.create("https://0.0.0.0:1990/files")).build()
    val request = RequestBuilder.create("GET")
        .setCharset(Charset.forName("UTF-8"))
        .setUri(URI.create("http://localhost:8000/test?src=C:/ksis/src.txt")).build()

    val response = httpClient.execute(request)
    if(response.statusLine.statusCode == 200) {
        println(response.entity.content.bufferedReader().readText())
    }
}