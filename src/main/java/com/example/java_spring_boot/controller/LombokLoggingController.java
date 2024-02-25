package com.example.java_spring_boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LombokLoggingController {

    @RequestMapping("/logging")
    public String logging() {
        log.trace("a trace log");
        log.debug("a debug log");
        log.info("a info log");
        log.warn("a warn log");
        log.error("a error log");

        return "check out the logs to see the output...";
    }
}
