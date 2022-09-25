package com.centime.assignment.controller;

import com.centime.assignment.exception.ServerError;
import com.centime.assignment.model.PersonName;
import com.centime.assignment.service.ServiceOneService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service-one")
public class ServiceOneController {

    private static final Logger LOG = LogManager.getLogger(ServiceOneController.class);

    @Autowired
    private ServiceOneService serviceOneService;

    @GetMapping("/health")
    public ResponseEntity<Health> checkServiceHealth() throws ServerError {
        LOG.info("inside service health controller method.");
        return serviceOneService.checkServiceHealth();
    }

    @PostMapping("/concatenate")
    public ResponseEntity<String> concatenatedMessage(@Valid @RequestBody PersonName personName) throws ServerError {
        LOG.info("inside concatenate message controller method.");
        return serviceOneService.concatenatedMessage(personName);
    }
}
