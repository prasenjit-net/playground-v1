package com.example.camelpocagain.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("http").host("localhost:8080");

        from("direct:callRest").to("rest:post:message");
    }
}
