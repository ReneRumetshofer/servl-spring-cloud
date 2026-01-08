package dev.rumetshofer.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${test}")
    private String test;

    @GetMapping
    public String test() {
        return test;
    }
}