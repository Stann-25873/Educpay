package com.edupay.mapper;

import com.edupay.dto.response.NotificationResponse;
import com.edupay.entity.Notification;
import com.edupay.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setType(notification.getType());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());

        response.setCreatedAt(notification.getCreatedAt());

        if (notification.getInstitution() != null) {
            response.setInstitutionId(notification.getInstitution().getId());
        }

        User recipient = notification.getRecipient();
        if (recipient != null) {
            response.setRecipientId(recipient.getId());
            response.setRecipientName(recipient.getFirstName() + " " + recipient.getLastName());
        }

        return response;
    }
}