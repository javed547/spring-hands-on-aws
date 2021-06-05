package com.javed.service.aws.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private static final Logger logger = LogManager.getLogger(SQSController.class);

    @GetMapping("/health/status")
    public ResponseEntity<String> healthStatus(){
        logger.info("calling method : StatusController:healthStatus");
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
}
