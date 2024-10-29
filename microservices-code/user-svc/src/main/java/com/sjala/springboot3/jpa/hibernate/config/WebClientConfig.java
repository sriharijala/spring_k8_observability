package com.sjala.springboot3.jpa.hibernate.config;
import com.sjala.springboot3.jpa.hibernate.client.ReviewSvcClient;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Autowired
    private ObservationRegistry observationRegistry;

    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl("http://user-svc")
                .observationRegistry(observationRegistry)  // Enabling observability
                .filter(filterFunction)
                .build();
    }

    @Bean
    public ReviewSvcClient userSvcClient() {


        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient()))
                .build();
        return httpServiceProxyFactory.createClient(ReviewSvcClient.class);
    }
}
