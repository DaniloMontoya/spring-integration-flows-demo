package com.dc.danilomontoya.springintegrationflows.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.LoggingHandler;

import java.io.File;
import java.net.URL;

/**
 * @author Ing. Danilo Montoya Hernandez;
 * Email: danilo9831montoya@gmail.com
 * @version Id: <b>spring-integration-flows</b> 13/09/2023, 10:48 AM
 **/
@Slf4j
@Configuration
public class RssInboundFlow {

    @Bean
    public StandardIntegrationFlow feedSubFlow() {
        try {
            return IntegrationFlow.from(Feed.inboundAdapter(new URL("https://lorem-rss.herokuapp.com/feed"), "georss"),
                            e -> e.poller(Pollers.fixedDelay(5000))
                                    .autoStartup(false))
                    .log()
                    .enrichHeaders(h -> h.header("source", "spring.blog"))
                    .channel("news")
                    .get();
        } catch (Exception ex) {
            log.error("Error to load feed flow");
        }
        return null;
    }

    @Bean
    public IntegrationFlow newsFlow() {
        return IntegrationFlow.from("news")
                .log(LoggingHandler.Level.INFO)
                .transform("headers['source'] + ':' + payload.title + ' @ ' + payload.link + '" + "\n" + "'") // SpEL
                .handle(Files.outboundAdapter(new File("/tmp/si/"))
                        .fileNameExpression("'SpringBlogDSL'")
                        .fileExistsMode(FileExistsMode.APPEND))
                .get();
    }
}
