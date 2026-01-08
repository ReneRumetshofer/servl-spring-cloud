package dev.rumetshofer.configanddiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableConfigServer
@EnableEurekaServer
@SpringBootApplication
public class ConfigAndDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigAndDiscoveryServerApplication.class, args);
    }

}
