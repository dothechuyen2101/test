package com.banking.notification.application.service;

import com.banking.notification.application.channel.NotificationChannel;
import com.banking.notification.application.dto.NotificationResponse;
import com.banking.notification.application.dto.SendNotificationRequest;
import com.banking.notification.application.template.TemplateEngine;
import com.banking.notification.domain.entity.Notification;
import com.banking.notification.domain.entity.NotificationStatus;
import com.banking.notification.domain.entity.NotificationTemplate;
import com.banking.notification.domain.service.NotificationDomainService;
import com.banking.notification.infrastructure.repository.NotificationRepository;
import com.banking.notification.infrastructure.repository.NotificationTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.notification.domain.entity.NotificationChannelType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository templateRepository;
    private final NotificationDomainService notificationDomainService;
    private final TemplateEngine templateEngine;
    private final Map<NotificationChannelType, NotificationChannel> channels = new EnumMap<>(NotificationChannelType.class);

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationTemplateRepository templateRepository,
            NotificationDomainService notificationDomainService,
            TemplateEngine templateEngine,
            List<NotificationChannel> channelBeans) {
        this.notificationRepository = notificationRepository;
        this.templateRepository = templateRepository;
        this.notificationDomainService = notificationDomainService;
        this.templateEngine = templateEngine;
        for (NotificationChannel ch : channelBeans) {
            channels.put(ch.getChannelType(), ch);
        }
    }

    @Override
    @Transactional
    public NotificationResponse send(SendNotificationRequest request) {
        Notification n = new Notification();
        n.setChannel(request.getChannel());
        n.setRecipient(request.getRecipient());
        n.setTemplateCode(request.getTemplateCode());

        if (request.getTemplateCode() != null) {
            NotificationTemplate tpl = templateRepository.findByCode(request.getTemplateCode())
                    .orElseThrow(() -> new com.banking.common.exception.ResourceNotFoundException(
                            "NotificationTemplate", "code", request.getTemplateCode()));
            n.setSubject(templateEngine.render(tpl.getSubjectTemplate(), request.getTemplateVariables()));
            n.setBody(templateEngine.render(tpl.getBodyTemplate(), request.getTemplateVariables()));
        } else {
            n.setSubject(request.getSubject());
            n.setBody(request.getBody());
        }

        notificationDomainService.validate(n);
        Notification saved = notificationRepository.save(n);

        NotificationChannel channel = channels.get(saved.getChannel());
        if (channel == null) {
            saved.setStatus(NotificationStatus.FAILED);
            saved.setErrorMessage("No channel implementation");
            notificationRepository.save(saved);
        } else {
            try {
                channel.dispatch(saved);
                saved.setStatus(NotificationStatus.SENT);
            } catch (Exception e) {
                saved.setStatus(NotificationStatus.FAILED);
                saved.setErrorMessage(e.getMessage());
            }
            notificationRepository.save(saved);
        }

        NotificationResponse r = new NotificationResponse();
        r.setId(saved.getId());
        r.setChannel(saved.getChannel());
        r.setStatus(saved.getStatus());
        r.setRecipient(saved.getRecipient());
        return r;
    }
}
