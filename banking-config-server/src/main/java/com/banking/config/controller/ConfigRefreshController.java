package com.banking.config.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigRefreshController {

    @PostMapping("/refresh-hint")
    public Map<String, String> refreshHint() {
        return Map.of(
                "message",
                "Config clients should POST /actuator/refresh (and optionally use Spring Cloud Bus for broadcast).",
                "serverLabel",
                "banking-config-server"
        );
    }
}
