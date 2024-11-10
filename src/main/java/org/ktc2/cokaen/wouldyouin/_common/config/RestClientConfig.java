package org.ktc2.cokaen.wouldyouin._common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient() {
        return RestClient.builder().build();
    }
}