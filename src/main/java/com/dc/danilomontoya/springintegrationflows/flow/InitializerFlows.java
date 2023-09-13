package com.dc.danilomontoya.springintegrationflows.flow;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Component;

/**
 * @author Ing. Danilo Montoya Hernandez;
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>spring-integration-flows</b> 13/09/2023, 10:43 AM
 **/
@Slf4j
@EnableIntegration
@Component
@RequiredArgsConstructor
public class InitializerFlows {
    private final IntegrationFlowContext integrationFlowContext;
    private final StandardIntegrationFlow pollerFlow;
    private final StandardIntegrationFlow feedSubFlow;
    StandardIntegrationFlow udpFlow;

    @PostConstruct
    public void init() {
        integrationFlowContext.registration(pollerFlow)
                .id("HttpSubFlowKEY")
                .register()
                .start();

        integrationFlowContext.registration(feedSubFlow)
                .id("RssFeedSubFlowKEY")
                .register();

        integrationFlowContext.registration(udpFlow)
                .id("UdpSubFlowKEY")
                .register();
    }
}
