package com.edupay.service;

import com.edupay.dto.response.NotificationResponse;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationResponse createNotification(UUID recipientId, String type, String title, String message);
    List<NotificationResponse> getNotificationsForCurrentUser();
    long getUnreadCount();
    void markAsRead(UUID id);
    void markAllAsRead();
}
