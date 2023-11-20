package com.example.camelpocagain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@RestController
public class HClientController {

    @GetMapping("/test-h-client")
    public String test() {
        HttpClient client = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "user",
                                "password".toCharArray());
                    }
                }).followRedirects(HttpClient.Redirect.NEVER)
                .version(HttpClient.Version.HTTP_2)
                .build();
        var request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/message"))
                .POST(HttpRequest.BodyPublishers.ofString("World"))
                .build();
        client.sendAsync(request, null);
        return "Hello World!";
    }
}
