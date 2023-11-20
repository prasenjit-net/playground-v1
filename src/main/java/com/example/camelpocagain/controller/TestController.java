package com.example.camelpocagain.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ProducerTemplate producerTemplate;

    public TestController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @GetMapping("/test")
    public String test() {
        return producerTemplate.requestBody("direct:callRest", "World", String.class);
    }

    @PostMapping("/message")
    public String message(@RequestBody String name) {
        return "Hello " + name + "!";
    }
}
