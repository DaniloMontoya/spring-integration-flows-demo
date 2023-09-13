package com.dc.danilomontoya.springintegrationflows.flow;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ing. Danilo Montoya Hernandez;
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>spring-integration-flows</b> 13/09/2023, 10:35 AM
 **/
@Slf4j
@EnableIntegration
@Component
@RequiredArgsConstructor
public class HttpOutboundFlowWithPoller {
    public static final int PERIOD_POLLER_TIME_MILLIS = 30 * 1000;
    public static final String WAZE_ALERTS_URL = "https://api.publicapis.org/entries";

    @Bean
    public StandardIntegrationFlow pollerFlow() {
        return IntegrationFlow
                .from(() -> new GenericMessage<>(""),
                        e -> e.poller(Pollers.fixedDelay(PERIOD_POLLER_TIME_MILLIS)))
                .handle(Http.outboundGateway(WAZE_ALERTS_URL)
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class)
                        .extractPayload(true)
                )
                .channel("outputChannelHttp")
                .get();
    }

    @Bean
    public RestTemplate requestFactory() {
        return new RestTemplate(); // Customize the RestTemplate if needed
    }

    @Bean
    public IntegrationFlow handleHttpResponse() {
        return IntegrationFlow.from("outputChannelHttp")
                .handle((Message<?> message) -> {
                    String response = message.getPayload().toString();
                    // Handle the HTTP response here
                    log.info("Received HTTP Response: " + response);
                })
                .get();
    }
}
