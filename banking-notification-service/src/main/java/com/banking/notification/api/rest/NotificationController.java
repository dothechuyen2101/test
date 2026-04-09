package com.banking.notification.api.rest;

import com.banking.notification.application.dto.NotificationResponse;
import com.banking.notification.application.dto.SendNotificationRequest;
import com.banking.notification.application.service.NotificationService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<NotificationResponse> send(@Valid @RequestBody SendNotificationRequest request) {
        return ApiResponse.success(notificationService.send(request));
    }
}
