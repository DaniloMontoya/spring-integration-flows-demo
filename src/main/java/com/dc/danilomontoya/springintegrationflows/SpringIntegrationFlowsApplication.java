package com.dc.danilomontoya.springintegrationflows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class SpringIntegrationFlowsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationFlowsApplication.class, args);
    }

}
