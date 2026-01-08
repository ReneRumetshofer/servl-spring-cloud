package dev.rumetshofer.productservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class AcrossTestController {

    private final RestTemplate restTemplate;

    public AcrossTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String test() {
        System.out.println("Here");
        return restTemplate.getForObject("http://order-service/test", String.class);
    }

}
