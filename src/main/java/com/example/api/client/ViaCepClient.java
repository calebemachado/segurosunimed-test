package com.example.api.client;

import com.example.api.client.response.AddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ViaCepClient {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepClient.class);
    @Value("${viacep.url}")
    private String viaCepUrl;

    public Optional<AddressResponse> getJsonAddressByZipcode(String zipcode) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = viaCepUrl.concat(zipcode).concat("/json");

        try {
            ResponseEntity<AddressResponse> responseEntity = restTemplate.getForEntity(apiUrl, AddressResponse.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                AddressResponse responseBody = responseEntity.getBody();
                logger.info("Response: " + responseBody);

                return Optional.ofNullable(responseBody);
            } else {
                logger.error("Unexpected HTTP status: " + responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                logger.error("Client error: " + e.getStatusCode() + " - " + e.getStatusText());
                logger.error("Response Body: " + e.getResponseBodyAsString());
            } else {
                logger.error("Unexpected HTTP status: " + e.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage());
        }

        return Optional.empty();
    }
}
