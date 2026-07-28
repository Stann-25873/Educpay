package com.edupay.serviceImpl;

import com.edupay.dto.response.NotificationResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Notification;
import com.edupay.entity.User;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.NotificationMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.NotificationRepository;
import com.edupay.repository.UserRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                    InstitutionRepository institutionRepository,
                                    UserRepository userRepository,
                                    NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional
    public NotificationResponse createNotification(UUID recipientId, String type, String title, String message) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Notification notification = new Notification();
        notification.setInstitution(institution);
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setIsRead(false);

        if (recipientId != null) {
            User recipient = userRepository.findById(recipientId)
                    .orElseThrow(() -> new ResourceNotFound("User not found: " + recipientId));
            if (!recipient.getTenantId().equals(tenantId)) {
                throw new UnauthorizedTenantAccess("Cross-tenant access denied");
            }
            notification.setRecipient(recipient);
        }

        notification = notificationRepository.save(notification);
        return notificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsForCurrentUser() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        String username = SecurityUtils.getCurrentUsername().orElseThrow();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFound("Current user not found"));
        return notificationRepository.findAll().stream()
                .filter(n -> n.getTenantId().equals(tenantId)
                        && n.getRecipient() != null
                        && n.getRecipient().getId().equals(user.getId()))
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        String username = SecurityUtils.getCurrentUsername().orElseThrow();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFound("Current user not found"));
        return notificationRepository.findAll().stream()
                .filter(n -> n.getTenantId().equals(tenantId)
                        && n.getRecipient() != null
                        && n.getRecipient().getId().equals(user.getId())
                        && Boolean.FALSE.equals(n.getIsRead()))
                .count();
    }

    @Override
    @Transactional
    public void markAsRead(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Notification not found: " + id));
        if (!notification.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        String username = SecurityUtils.getCurrentUsername().orElseThrow();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFound("Current user not found"));
        List<Notification> unread = notificationRepository.findAll().stream()
                .filter(n -> n.getTenantId().equals(tenantId)
                        && n.getRecipient() != null
                        && n.getRecipient().getId().equals(user.getId())
                        && Boolean.FALSE.equals(n.getIsRead()))
                .toList();
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }
}