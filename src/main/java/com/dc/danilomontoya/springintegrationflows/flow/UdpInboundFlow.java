package com.dc.danilomontoya.springintegrationflows.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.integration.ip.dsl.UdpInboundChannelAdapterSpec;

/**
 * @author Ing. Danilo Montoya Hernandez;
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>spring-integration-flows</b> 13/09/2023, 10:52 AM
 **/
@Configuration
public class UdpInboundFlow {
    @Bean
    public StandardIntegrationFlow udpFlow() {
        UdpInboundChannelAdapterSpec udpInboundChannelAdapterSpec = Udp
                .inboundMulticastAdapter(1234, "172.168.2.1")
                .autoStartup(false)
                .localAddress("172.168.2.3").lookupHost(false);

        return IntegrationFlow.from(udpInboundChannelAdapterSpec)
                .log(LoggingHandler.Level.INFO)
                .get();
    }
}
