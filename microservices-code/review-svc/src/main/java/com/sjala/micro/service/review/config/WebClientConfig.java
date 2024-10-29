package com.sjala.micro.service.review.config;
import com.sjala.micro.service.review.client.UserSvcClient;
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
                .baseUrl("http://review-svc")
                .observationRegistry(observationRegistry)  // Enabling observability
                .filter(filterFunction)
                .build();
    }

    @Bean
    public UserSvcClient userSvcClient() {


        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient()))
                .build();
        return httpServiceProxyFactory.createClient(UserSvcClient.class);
    }
}
