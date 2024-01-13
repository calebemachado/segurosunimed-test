package com.example.api;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class ApiApplication {

    private final CustomerRepository customerRepository;

    public ApiApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner populateDatabase() {
        return args -> {
            customerRepository.save(new Customer("HOMEM ARANHA", "aranha@vingadores.com", "M"));
            customerRepository.save(new Customer("THOR", "thor@vingadores.com", "M"));
            customerRepository.save(new Customer("VIUVA NEGRA", "viuva@vingadores.com", "F"));
            customerRepository.save(new Customer("NAMOR", "namor@vingadores.com", "M"));
            customerRepository.save(new Customer("GAMORA", "gamora@vingadores.com", "F"));
            customerRepository.save(new Customer("Hulk", "hulk@vingadores.com", "m"));
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }
}
