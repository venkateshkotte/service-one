package com.centime.assignment.service;

import com.centime.assignment.exception.ServerError;
import com.centime.assignment.model.PersonName;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Service
public class ServiceOneService {

    private static final Logger LOG = LogManager.getLogger(ServiceOneService.class);
    public static final int threads = 5;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.two.endpoint}")
    private String serviceTwoEndpoint;

    @Value("${service.three.endpoint}")
    private String serviceThreeEndpoint;

    public ResponseEntity<Health> checkServiceHealth() throws ServerError {
        LOG.info("inside service up service method.");
        try {
            restTemplate.getForObject(serviceTwoEndpoint + "health", String.class);
        } catch (Exception e) {
            LOG.error("Exception occurred in service-two status check", e);
            throw new ServerError("service-two - temporarily unavailable",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
        try {
            restTemplate.getForObject(serviceThreeEndpoint + "health", String.class);
        } catch (Exception e) {
            LOG.error("Exception occurred in service-three status check", e);
            throw new ServerError("service-three - temporarily unavailable",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(Health.up().build(), HttpStatus.OK);
    }

    public ResponseEntity<String> concatenatedMessage(PersonName personName) throws ServerError {
        LOG.info("inside concatenate message service method.");
        CompletableFuture<String> helloGetAPICall = CompletableFuture.supplyAsync(() ->
            restTemplate.getForObject(serviceTwoEndpoint + "hello", String.class)
                ,new TraceableExecutorService(this.beanFactory, Executors.newFixedThreadPool(threads), "concatenatedMessage")
        );
        CompletableFuture<String> postAPICall = CompletableFuture.supplyAsync(() ->
                restTemplate.postForObject(serviceThreeEndpoint + "concatenate", personName, String.class),
                new TraceableExecutorService(this.beanFactory, Executors.newFixedThreadPool(threads), "concatenatedMessage")
        );
        String helloAPI;
        String concatenateAPI;
        try {
            helloAPI = helloGetAPICall.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Exception occurred while making hello get API call ", e);
            throw new ServerError("Temporarily service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
        try {
            concatenateAPI = postAPICall.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Exception occurred while making concatenate POST API call ", e);
            throw new ServerError("Temporarily service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (StringUtils.isBlank(helloAPI) || StringUtils.isBlank(concatenateAPI)) {
            throw new ServerError("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(helloAPI +
                " " +
                concatenateAPI,
                HttpStatus.OK);
    }
}
